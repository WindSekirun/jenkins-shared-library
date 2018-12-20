#!/usr/bin/env groovy

/**
 * Send notifications based on build status string
 */
def call(String buildStatus = 'STARTED') {
  // build status of null means successful
  buildStatus = buildStatus ?: 'SUCCESS'

  // Default values
  def colorName = 'RED'
  def colorCode = '#FF0000'
  def subject = "${buildStatus}: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'"
  def summary = "${subject} (${env.BUILD_URL})"
  def details = "Build for ${env.JOB_NAME} [${env.BUILD_NUMBER}] is ${buildStatus}! Check console output at (${env.BUILD_URL})"

  // Override default values based on build status
  if (buildStatus == 'STARTED') {
    color = 'YELLOW'
    colorCode = '#FFFF00'
  } else if (buildStatus == 'SUCCESS') {
    color = 'GREEN'
    colorCode = '#00FF00'
  } else {
    color = 'RED'
    colorCode = '#FF0000'
  }

  // Send notifications
  slackSend (color: colorCode, message: details)
  
  // Telegram message
  def targetUserId = "47220554"
  def botToken = "674287229:AAFF9Pc0kUrZsL1p3-nIBKB7FGmHeIobNr0"
  def message = java.net.URLEncoder.encode(details, "utf-8")
  println new URL("https://api.telegram.org/bot$botToken/sendMessage?chat_id=$targetUserId&text=$message").getText()
}
