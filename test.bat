
REM Compile all Java files
powershell -Command "javac -d out (Get-ChildItem -Recurse -Filter *.java src | %% { $_.FullName })"

REM java -cp out ExpenseTrackerCLI add --amount 10.0001 --description true --category "sabzi"

java -cp out ExpenseTrackerCLI export

