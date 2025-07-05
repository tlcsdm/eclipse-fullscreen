# Fullscreen

With this small plugin Eclipse and RCP Programs based on Eclipse can run in full screen mode, so that you can have more space to edit your programs.

## Use
1. Menu Window/Full Screen enter full screen mode.
2. Ctrl+ALT+Z enter or exit full screen mode.
3. ESC exit full screen mode.

## Build

This project uses [Tycho](https://github.com/eclipse-tycho/tycho) with [Maven](https://maven.apache.org/) to build. It requires Maven 3.9.0 or higher version.

Dev build:

```
mvn clean verify
```

Release build:

```
mvn clean org.eclipse.tycho:tycho-versions-plugin:set-version -DnewVersion=2.0.0 verify
```

## Install

1. Add `https://raw.githubusercontent.com/tlcsdm/eclipse-fullscreen/master/update_site/` as the upgrade location in Eclipse.
2. Download from [Jenkins](https://jenkins.tlcsdm.com/job/eclipse-plugin/job/eclipse-fullscreen)

