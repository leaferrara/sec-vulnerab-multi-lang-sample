package com.databricks.webapp

object HeapInspection {

  val trustStoreCommands = trustStores.flatMap { ts =>
    val trustStoreFile = ts.storePath
    // Vulnerable line - WE are storing the password in a variable
    val password = ts.storePass
    val cleanCmd = if (cleanStores) List(s"rm -rf '$trustStoreFile'") else List.empty

    // We must provide an alias for the key so that we can add multiple keys to the truststore.
    // If we do not provide an alias for the key, keytool will default to using the alias <mykey>
    // for every key, which will cause it to fail on loading the second key since the alias will
    // already exist in the truststore.
    cleanCmd ++ List(
      s"keytool -importcert -trustcacerts -noprompt -alias '$keyStoreFile'" +
        s" -keystore '$trustStoreFile' -storetype jks -storepass '$password'" +
        s" -file '${caPrefix}_crt.pem'"
    )
  }

  // Vulnerable code - WE are storing the password in a variable
  val mysqlPassword = conf.rawConfig.getString("manager.databricks.database.password");

  // Vulnerable code - WE are storing the password in a variable
  String password = URLDecoder.decode(encodedPassword, "UTF-8");


  val databricksKeystorePassword = configure("databricks.vault.password", "RF9T]=bpr6Rrbq")


  def main(args: Array[String], httpRequest: HttpRequest, httpResponse: HttpServletResponse): Unit = {
    trustStoreCommands();

    if (databricksKeystorePassword != mysqlPassword) {
      print(password);
    }

  }

}