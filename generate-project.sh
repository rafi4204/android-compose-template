#!/bin/bash
set -e

# Script inspired by https://gist.github.com/szeidner/613fe4652fc86f083cefa21879d5522b

# Enable logging
# set -x

PROGNAME=$(basename $0)
WORKING_DIR=$(cd .. && pwd -P)

cd $WORKING_DIR

die() {
    echo "$PROGNAME: $*" >&2
    exit 1
}

usage() {
    if [ "$*" != "" ] ; then
        echo "Error: $*"
    fi

    cat << EOF
Usage: $PROGNAME --package-name [PACKAGE_NAME] --app-name [APP_NAME]
Set up an Android app and package name.
Options:
-h, --help                         display this usage message and exit
-p, --package-name [PACKAGE_NAME]  new package name (i.e. com.example.package)
-n, --app-name [APP_NAME]          new app name (i.e. MyApp, "My App")
EOF

    exit 1
}

packagename=""
appname=""
while [ $# -gt 0 ] ; do
    case "$1" in
    -h|--help)
        usage
        ;;
    -p|--package-name)
        packagename="$2"
        shift
        ;;
    -n|--app-name)
        appname="$2"
        shift
        ;;
    -*)
        usage "Unknown option '$1'"
        ;;
    *)
        usage "Too many arguments"
      ;;
    esac
    shift
done

OLD_APPNAME=""
OLD_NAME=""
OLD_PACKAGE=""

# Path segments
FIRST_PACKAGE_SEGMENT="com"
SECOND_PACKAGE_SEGMENT="monstarlab"

OLD_APPNAME="android-template"
OLD_NAME="android-template"
OLD_PACKAGE="com.monstarlab"

if [ -z "$packagename" ] ; then
    usage "No new package provided"
fi

if [ -z "$appname" ] ; then
    usage "No new app name provided"
fi

# Enforce package name
regex='^[a-z][a-z0-9_]*(\.[a-z0-9_]+)+[0-9a-z_]$'
if ! [[ "$packagename" =~ $regex ]]; then
    die "Invalid Package Name: $packagename (needs to follow standard pattern {com.example.package})"
fi

echo "=> 🐢 Staring init $appname with $OLD_APPNAME..."

# Trim spaces in APP_NAME
NAME_NO_SPACES=$(echo "$appname" | sed "s/ //g")

# Copy main folder
cp -R $OLD_NAME $NAME_NO_SPACES

# Clean the old build
./$NAME_NO_SPACES/gradlew -p ./$NAME_NO_SPACES clean
# Get rid of idea settings
rm -rf $NAME_NO_SPACES/.idea
# Get rid of gradle cache
rm -rf $NAME_NO_SPACES/.gradle
# Get rid of the git history
rm -rf $NAME_NO_SPACES/.git

# Rename folder structure
renameFolderStructure() {
  DIR=""
  if [ "$*" != "" ] ; then
      DIR="$*"
  fi
  ORIG_DIR=$DIR

  mkdir $NAME_NO_SPACES/$DIR/backup

  mv $NAME_NO_SPACES/$DIR/$FIRST_PACKAGE_SEGMENT/$SECOND_PACKAGE_SEGMENT/* $NAME_NO_SPACES/$DIR/backup
  grep -l '.*' $NAME_NO_SPACES/$DIR/*
  rm -rf $NAME_NO_SPACES/$DIR/$FIRST_PACKAGE_SEGMENT
  cd $NAME_NO_SPACES/$DIR
  IFS='.' read -ra packages <<< "$packagename"
  for i in "${packages[@]}"; do
      DIR="$DIR/$i"
      mkdir $i
      cd $i
  done
  mv $WORKING_DIR/$NAME_NO_SPACES/$ORIG_DIR/backup/* ./
  rmdir $WORKING_DIR/$NAME_NO_SPACES/$ORIG_DIR/backup
  cd $WORKING_DIR
  echo $DIR
}

# Rename files structure
echo "=> 🔎 Replacing files structure..."

# Rename project folder structure
APP_PACKAGE_DIR="app/src/main/java"
APP_PACKAGE_DIR=$( renameFolderStructure $APP_PACKAGE_DIR )

# Rename android test folder structure
APP_ANDROIDTEST_DIR="app/src/androidTest/java"
APP_ANDROIDTEST_DIR=$( renameFolderStructure $APP_ANDROIDTEST_DIR )

# Rename test folder structure
APP_TEST_DIR="app/src/test/java"
APP_TEST_DIR=$( renameFolderStructure $APP_TEST_DIR )

echo "✅  Completed"

# Search and replace in files
echo "=> 🔎 Replacing package and package name within files..."
PACKAGE_NAME_ESCAPED="${packagename//./\.}"
OLD_PACKAGE_NAME_ESCAPED="${OLD_PACKAGE//./\.}"
if [[ "$OSTYPE" == "darwin"* ]] # Mac OSX
then
  LC_ALL=C find $WORKING_DIR/$NAME_NO_SPACES -type f -exec sed -i "" -e "s/$OLD_PACKAGE_NAME_ESCAPED/$PACKAGE_NAME_ESCAPED/g" {} +
  LC_ALL=C find $WORKING_DIR/$NAME_NO_SPACES -type f -exec sed -i "" -e "s/$OLD_NAME/$NAME_NO_SPACES/g" {} +
else
  LC_ALL=C find $WORKING_DIR/$NAME_NO_SPACES -type f -exec sed -i -e "s/$OLD_PACKAGE_NAME_ESCAPED/$PACKAGE_NAME_ESCAPED/g" {} +
  LC_ALL=C find $WORKING_DIR/$NAME_NO_SPACES -type f -exec sed -i -e "s/$OLD_NAME/$NAME_NO_SPACES/g" {} +
fi
echo "✅  Completed"

# Search and replace files <...>
echo "=> 🔎 Replacing app name in strings.xml and build.gradle ..."
if [[ "$OSTYPE" == "darwin"* ]] # Mac OSX
then
  sed -i "" -e "s/$OLD_APPNAME/$appname/" "$WORKING_DIR/$NAME_NO_SPACES/app/src/main/res/values/strings.xml"
  sed -i "" -e "s/Monstarlab/$appname/" "$WORKING_DIR/$NAME_NO_SPACES/app/build.gradle"
else
  sed -i -e "s/$OLD_APPNAME/$appname/" "$WORKING_DIR/$NAME_NO_SPACES/app/src/main/res/values/strings.xml"
  sed -i -e "s/Monstarlab/$appname/" "$WORKING_DIR/$NAME_NO_SPACES/app/build.gradle"
fi
echo "✅  Completed"

echo "=> 🛠️ Building generated project..."
./$NAME_NO_SPACES/gradlew -p ./$NAME_NO_SPACES assembleDebug
echo "✅  Build success"

# Done!
echo "=> 🚀 Done! The project is ready for development 🙌"

# Disable logging
# set +x
