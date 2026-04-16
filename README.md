# ShowNTLM

Android application to execute local scripts within a NetHunter/Kali environment and visualize output and logs.

A local network attack chain combining ARP poisoning (MITM) with network authentication interception, resulting in the capture of NetNTLM challenge-response hashes over protocols such as SMB, LLMNR, and NBNS.

## Features

* Script execution with parameters (root required)
* Output visualization in real time
* Log file viewer
* Simple tab-based UI

## Screens

* Bettercap (script runner)
* Responder (script runner)
* Logs viewer
* About

## Requirements

* Rooted Android device
* Kali NetHunter environment
* Scripts located in:

    * `/data/local/nhsystem/`

## Example scripts

```
/data/local/nhsystem/run_better.sh
/data/local/nhsystem/run_responder.sh
```

## Disclaimer

This tool is intended for authorized testing and lab environments only.

## Author

Walter Magliano
