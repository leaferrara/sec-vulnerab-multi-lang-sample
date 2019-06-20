package com.databricks.webapp

import java.net.URLEncoder
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

object ReflectedXss {

  def createEmptyHandler(basePath: String): ApiHandler = {
    new ApiHandler(basePath, Map.empty)
  }


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

    new ApiHandler(basePath, handlers)
  }
}

class ApiHandler(
                  val basePath: String,
                  @VisibleForTesting
                  val apiHandlers: Map[String, Handler]) extends AbstractHandler with Logging {

  require(!basePath.endsWith("/"), "base path cannot end in `/`")


  private def findHandler(target: String): Handler = {
        return apiHandlers(trimmedPath)
  }

  override def handle(
                       target: String,
                       baseRequest: Request,
                       request: HttpServletRequest,
                       response: HttpServletResponse): Unit = {
    try {
      val realTarget = target.replaceAll("^" + basePath, "")
      var trimmedTarget = realTarget

      val handler: Handler = findHandler(trimmedTarget)
      handler.handle(realTarget, baseRequest, request, response)
    } catch {
      case NonFatal(e) =>
        response.setContentType("application/json; charset=UTF-8")
        val sanitizedRequestURI = request.getRequestURI.matches("^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]")
        response.getWriter.println(ErrorResult(s"Bad Target: ${sanitizedRequestURI}").toJson)
        response.setStatus(HttpServletResponse.SC_NOT_FOUND)
    }
  }
}

