rem @echo off

for %%i in (*.t) do (
  copy %%i %%~ni > nul
)
call javac TestGen.java
call java TestGen
call tall rg2
call docheck rg2

