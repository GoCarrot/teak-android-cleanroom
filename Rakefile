require "rake/clean"
require "shellwords"
CLEAN.include "**/.DS_Store"

desc "Build Adobe Air package"
task :default

PROJECT_PATH = Rake.application.original_dir

#
# Helper methods
#
def gradle(*args)
  escaped_args = args.map { |arg| Shellwords.escape(arg) }.join(' ')
  sh "./gradlew #{escaped_args}"
end

#
# Tasks
#
task :clean do
  gradle "clean" # Remove this when done
  #sh "git clean -fdx"
  puts "DO CLEAN HERE WHEN DONE"
end

namespace :package do
  task download: [:clean] do
    sh "curl -o app/libs/teak.aar https://s3.amazonaws.com/teak-build-artifacts/android/teak.aar"
  end

  task copy: [:clean] do
    cp '../teak-android/build/outputs/aar/teak-release.aar', 'app/libs/teak.aar'
  end
end

task :test do
  # TODO: "-Dcleanroom_sdk_version=DOWNLOADED_VERSION"
  gradle "test"
end

task :connectedTest do
  gradle "connectedAndroidTest"
end

task :install do

end
