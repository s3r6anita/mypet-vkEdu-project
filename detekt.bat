@ECHO OFF
TITLE Check style with Detekt
SET ROOT_FOLDER=build\bin
SET VERSION=1.23.5
SET DETEKT_ZIP=detekt-cli-%VERSION%.zip
SET DETEKT_BIN=%ROOT_FOLDER%\detekt-cli-%VERSION%-all.jar
if not exist %DETEKT_BIN% (
  ECHO Please wait, first download...
  if not exist %ROOT_FOLDER% MKDIR %ROOT_FOLDER%
  curl.exe -sSLO "https://github.com/detekt/detekt/releases/download/v%VERSION%/detekt-cli-%VERSION%.zip"
  7z e "%DETEKT_ZIP%" -o"%ROOT_FOLDER%"
  del "%DETEKT_ZIP%"
)

java -jar %DETEKT_BIN% --config .github/workflows/assets/detekt.yml
echo Done!
