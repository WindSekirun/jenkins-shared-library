#!/usr/bin/env groovy

/**
 * Send notifications based on build status string
 * Modified on UzukiLiveBot style
 */
def call(String buildStatus = 'STARTED') {
  buildStatus = buildStatus ?: 'SUCCESS'

  // Default values
  def projectInfo = "${env.JOB_NAME} [${env.BUILD_NUMBER}]"
  def details = "Build for ${projectInfo} is ${buildStatus}! Check console output at (${env.BUILD_URL})"

  // Override default values based on build status
  if (buildStatus == 'STARTED') {
    details = "Build ${projectInfo} is started! 島村卯月、がんばります！ ☆ﾐ(o*･ω･)ﾉ"
  } else if (buildStatus == 'SUCCESS') { // The build had no errors.
    details = "Build ${projectInfo} was successful! 一度もミスなく出来ました！ (*・ω・)ﾉ"
  } else if (buildStatus == 'FAILURE') { // The build had a fatal error.
    details = "Build ${projectInfo} was failed... 頑張るだけじゃ、ダメなのかなぁ…う～… (っ˘̩╭╮˘̩)っ"
  } else if (buildStatus == 'UNSTABLE') { // The build had some errors but they were not fatal. 
    details = "Build ${projectInfo} was unstabled... あっ…甘くて苦くてせつない気持ちって…こういうこと、かな？ (._."
  } else if (buildStatus == 'ABORTED') { // The build was manually aborted
    details = "Build ${projectInfo} was aborted... あの…プロデューサーさん？立ったままですけど、大丈夫ですか～？ (⇀_⇀)"
  }
  
  // Telegram message
  // send @WindSekirun (47220554) to message
  def targetUserId = "47220554"
  def botToken = "674287229:AAFF9Pc0kUrZsL1p3-nIBKB7FGmHeIobNr0"
  def message = java.net.URLEncoder.encode(details, "utf-8")
  println new URL("https://api.telegram.org/bot$botToken/sendMessage?chat_id=$targetUserId&text=$message").getText()
}
