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
    details = "'${projectInfo}'의 빌드를 시작했어요! 🙇‍♀️(${env.BUILD_URL})"
  } else if (buildStatus == 'SUCCESS') { // The build had no errors.
    details = "'${projectInfo}'의 빌드를 성공적으로 마쳤어요! (*・ω・)ﾉ (${env.BUILD_URL})"
  } else if (buildStatus == 'FAILURE') { // The build had a fatal error.
    details = "'${projectInfo}'의 빌드를 실패했어요. 😱 이유가 뭘까요? 🤔 (${env.BUILD_URL})"
  } else if (buildStatus == 'UNSTABLE') { // The build had some errors but they were not fatal. 
    details = "'${projectInfo}'의 빌드가 이상한 것 같아요. 🤒 한번 살펴주세요! 🤔(${env.BUILD_URL})"
  } else if (buildStatus == 'ABORTED') { // The build was manually aborted
    details = "'${projectInfo}'의 빌드를 취소했어요. 🙋‍♀️ (${env.BUILD_URL})"
  }
  
  // Telegram message
  // send PyxisGroup (-369756639) to message
  def targetUserId = -369756639"
  def botToken = "674287229:AAFF9Pc0kUrZsL1p3-nIBKB7FGmHeIobNr0"
  def message = java.net.URLEncoder.encode(details, "utf-8")
  println new URL("https://api.telegram.org/bot$botToken/sendMessage?chat_id=$targetUserId&text=$message").getText()
}
