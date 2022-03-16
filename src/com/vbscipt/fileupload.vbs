Set WshShell = WScript.CreateObject("WScript.Shell")
WshShell.SendKeys "example.pdf"
WshShell.SendKeys "{ENTER}"