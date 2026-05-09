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
sudo rm /usr/local/bin/dfile /usr/local/lib/dfile.jar
```

### Windows

Run the following commands in **PowerShell as Administrator**:

```powershell
# Create install directory
New-Item -ItemType Directory -Force -Path "C:\Program Files\dfile"

# Copy the JAR
Copy-Item dist\DFile.jar "C:\Program Files\dfile\dfile.jar"

# Copy the wrapper script
Copy-Item dfile.bat "C:\Program Files\dfile\dfile.bat"

# Add to system PATH (requires restart of terminal)
[Environment]::SetEnvironmentVariable("Path", $env:Path + ";C:\Program Files\dfile", "Machine")
```

**Uninstall:**
```powershell
Remove-Item -Recurse -Force "C:\Program Files\dfile"
```

Once installed, use `dfile` directly:

```bash
dfile /home/user/documents list .mkv
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

| Operation | Description |
|---|---|
| `list` | Lists all files and directories recursively |
| `list <extension>` | Lists only files with the given extension |
| `count` | Shows total files, count per extension and total directories |
| `count <extension>` | Counts files with the given extension |
| `remove <ext1> [ext2 ...]` | Removes files with one or more extensions |
| `remove dir` | Removes the directory and all its contents |

### Examples

```bash
# List all files
java -jar dist/DFile.jar /home/user/documents list

# List only .mkv files
java -jar dist/DFile.jar /home/user/documents list .pdf

# Full stats (files per extension and directories)
java -jar dist/DFile.jar /home/user/documents count

# Count .nfo files
java -jar dist/DFile.jar /home/user/documents count .mp3

# Remove .exe files
java -jar dist/DFile.jar C:\Users\user\downloads remove .pdf

# Remove multiple extensions at once
java -jar dist/DFile.jar /tmp/folder remove .pdf .txt .mp3

# Delete an entire directory
java -jar dist/DFile.jar /tmp/temp-folder remove dir
```

## License

This project is governed by the [MIT License](/LICENSE). Just remember to be a nice person and send back any modifications, corrections or improvements. ✌️

## Author

| [<img src="https://avatars0.githubusercontent.com/u/8234620?" width="115"><br><sub>@moser-jose</sub>](https://github.com/moser-jose) |
| :----------------------------------------------------------------------------------------------------------------------------------: |

