# DFile

A Java command-line utility to manage files and directories recursively. Works on **Linux**, **Windows** and **macOS**.

## Requirements

- Java 8 or higher

## Build

### 1. Compile the source files

```bash
javac -d build/classes src/dfile/*.java
```

### 2. Package into a JAR

```bash
jar cfm dist/DFile.jar manifest.mf -C build/classes .
```

### Or both steps at once

```bash
javac -d build/classes src/dfile/*.java && jar cfm dist/DFile.jar manifest.mf -C build/classes .
```

## Tests

### Setup (first time only)

Download the JUnit 5 standalone runner:

```bash
mkdir -p lib && curl -L -o lib/junit-platform-console-standalone-1.10.3.jar \
  "https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/1.10.3/junit-platform-console-standalone-1.10.3.jar"
```

### Run tests

```bash
# Compile all tests
javac -cp lib/junit-platform-console-standalone-1.10.3.jar:build/classes \
  -d build/test-classes $(find test -name "*.java")

# Run all tests
java -jar lib/junit-platform-console-standalone-1.10.3.jar \
  --class-path build/classes:build/test-classes \
  --scan-class-path
```

---

## Install (optional)

To run `dfile` from anywhere without specifying the full JAR path. Build the JAR first (see above), then follow the steps for your OS.

### Linux and macOS

```bash
sudo cp dist/DFile.jar /usr/local/lib/dfile.jar
sudo cp dfile /usr/local/bin/dfile
sudo chmod +x /usr/local/bin/dfile
```

**Uninstall:**
```bash
sudo rm /usr/local/lib/dfile.jar /usr/local/bin/dfile
```

### Windows

Run the following commands in **PowerShell as Administrator**:

```powershell
# Create install directory
New-Item -ItemType Directory -Force -Path "C:\Program Files\dfile"

# Copy the JAR and wrapper script
Copy-Item dist\DFile.jar "C:\Program Files\dfile\dfile.jar"
Copy-Item dfile.bat "C:\Program Files\dfile\dfile.bat"

# Add to system PATH (requires restart of terminal)
[Environment]::SetEnvironmentVariable("Path", $env:Path + ";C:\Program Files\dfile", "Machine")
```

**Uninstall:**
```powershell
Remove-Item -Recurse -Force "C:\Program Files\dfile"
```

---

## Usage

```bash
# Without install
java -jar dist/DFile.jar <path> <operation> [options]

# With install
dfile <path> <operation> [options]
```

### Available operations

| Operation | Alias | Description |
|---|---|---|
| `list [extension]` | `ls` | Lists all files and directories recursively |
| `count [extension]` | `ct` | Shows total files, count per extension and total directories |
| `remove <ext1> [ext2 ...]` | `rm` | Removes files with one or more extensions |
| `remove dir\|-d` | `rm dir\|-d` | Removes the directory and all its contents |

### Examples

```bash
# List all files
dfile /home/user/documents list
dfile /home/user/documents ls

# List only .mkv files
dfile /home/user/documents list .mkv
dfile /home/user/documents ls .mkv

# Full stats (files per extension and directories)
dfile /home/user/documents count
dfile /home/user/documents ct

# Count .mp3 files
dfile /home/user/documents count .mp3
dfile /home/user/documents ct .mp3

# Remove .pdf files
dfile /home/user/downloads remove .pdf
dfile /home/user/downloads rm .pdf

# Remove multiple extensions at once
dfile /tmp/folder remove .nfo .txt .exe
dfile /tmp/folder rm .nfo .txt .exe

# Delete an entire directory
dfile /tmp/temp-folder remove dir
dfile /tmp/temp-folder rm dir
```

## License

This project is governed by the [MIT License](/LICENSE). Just remember to be a nice person and send back any modifications, corrections or improvements. ✌️

## Author

| [<img src="https://avatars0.githubusercontent.com/u/8234620?" width="115"><br><sub>@moser-jose</sub>](https://github.com/moser-jose) |
| :----------------------------------------------------------------------------------------------------------------------------------: |

