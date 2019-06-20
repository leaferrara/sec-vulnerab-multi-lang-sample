package com.databricks.webapp

import java.util.NoSuchElementException
import javax.servlet.http.{HttpServletRequest, HttpServletResponse}

import scala.util.control.NonFatal

import com.google.common.annotations.VisibleForTesting
import org.eclipse.jetty.server.{Handler, Request}
import org.eclipse.jetty.server.handler.AbstractHandler

import com.databricks.Logging
import com.databricks.apiclient.DbcRestApiMessage.ErrorResult
import com.databricks.apiserver.{ApiProxy, ApiProxyConf}
import com.databricks.backend.common.fs.FileStore
import com.databricks.backend.common.util.Project
import com.databricks.conf.trusted.ProjectConf
import com.databricks.elasticspark.acl.JobsAclInitializer
import com.databricks.rpc.JettyClient
import com.databricks.webapp.acl.WorkspaceAclService
import com.databricks.webapp.cluster.{WebClusterManager, WebClusterManagerPlugin}
import com.databricks.webapp.libraries.LibraryManager
import com.databricks.webapp.util.{UserSessionContextProvider, WebappConf}

object ApiHandler extends Logging {

  def createEmptyHandler(basePath: String): ApiHandler = {
    new ApiHandler(basePath, Map.empty)
  }

  /** Create the API handler for a single tenant */
  def createHandler(
                     basePath: String,
                     conf: WebappConf,
                     wClusterMgr: WebClusterManager,
                     managerPlugin: WebClusterManagerPlugin,
                     fileUploadHandler: FileUploadHandler,
                     fileStore: FileStore,
                     workspaceAclService: WorkspaceAclService,
                     libraryManager: LibraryManager,
                     systemTreeMgr: TreeManager,
                     extraHandlers: Map[String, Handler],
                     ignoreCSRFCheck: Boolean,
                     jobsAclInitializerOpt: Option[JobsAclInitializer],
                     userSessionContextProvider: UserSessionContextProvider): ApiHandler = {

    val webappHost = "localhost"
    val shardServicesClientOpt = new JettyClient(conf.centralClientTimeoutSeconds,
      conf.centralClientSslArgumentsOpt)

    val handlers = extraHandlers ++ Map(
      "/1.0" -> new ApiV1Handler(
        "/1.0",
        conf,
        wClusterMgr,
        managerPlugin,
        fileUploadHandler,
        fileStore,
        userSessionContextProvider,
        ignoreCSRFCheck = ignoreCSRFCheck),
      "/1.1" -> new ApiV11Handler(
        "/1.1",
        conf,
        systemTreeMgr,
        libraryManager,
        wClusterMgr,
        managerPlugin,
        fileUploadHandler,
        fileStore,
        workspaceAclService,
        userSessionContextProvider,
        ignoreCSRFCheck = ignoreCSRFCheck),
      "/1.2" -> new ApiV12Handler(
        "/1.2",
        conf,
        systemTreeMgr,
        libraryManager,
        wClusterMgr,
        managerPlugin,
        fileUploadHandler,
        fileStore,
        workspaceAclService,
        userSessionContextProvider,
        ignoreCSRFCheck = ignoreCSRFCheck),
      "/2.0" -> ApiProxy(
        "/2.0",
        new ApiProxyConf(ProjectConf.loadLocalConfig(Project.ApiServer)),
        webappHost,
        conf.clusterManagerHost,
        conf.sisyphusManagerHost,
        conf.s3CommitServiceHost,
        conf.elasticSparkHost,
        conf.secretManagerHost,
        conf.mlflowHost,
        conf.centralClientURI,
        Some(shardServicesClientOpt),
        ignoreCSRFCheck = ignoreCSRFCheck,
        jobsAclInitializerOpt))

    new ApiHandler(basePath, handlers)
  }
}

/**
  * A handler that multiplexes between a number of sub handlers under the basePath. A request for
  * basePath/subBasePath/foo will be routed to the apiHandler registered at subBasePath in the
  * map.
  */
class ApiHandler(
                  val basePath: String,
                  @VisibleForTesting
                  val apiHandlers: Map[String, Handler]) extends AbstractHandler with Logging {

  require(!basePath.endsWith("/"), "base path cannot end in `/`")


  private def findClosestHandler(target: String): Handler = {
    val paths = target.split("/")
    // iteratively remove subpath elements from right end of array
    // and rebuild path with mkString("/")
    (0 to (paths.length-2)).foreach(toDrop => {
      // new trimmed path with one more subpath dropped
      val trimmedPath = paths.dropRight(toDrop).mkString("/")
      if (apiHandlers.contains(trimmedPath)) {
        return apiHandlers(trimmedPath)
      }
    })
    throw new NoSuchElementException(s"No handler found for path $target")
  }

  override def handle(
                       target: String,
                       baseRequest: Request,
                       request: HttpServletRequest,
                       response: HttpServletResponse): Unit = {
    try {
      val realTarget = target.replaceAll("^" + basePath, "")
      // remove /api basepath
      var trimmedTarget = realTarget

      // will throw NoSuchElementException if no handler exists for any path
      val handler: Handler = findClosestHandler(trimmedTarget)
      handler.handle(realTarget, baseRequest, request, response)
    } catch {
      case NonFatal(e) =>
        response.setContentType("application/json; charset=UTF-8")
        // scalastyle:off println
        //val sanitizedRequestURI = request.getRequestURI.replaceAll("[\"\r\n]", "_") + "\""
        response.getWriter.println(ErrorResult(s"Bad Target: ${request.getRequestURI}").toJson)
        // scalastyle:on println
        response.setStatus(HttpServletResponse.SC_NOT_FOUND)
    }
  }
}

