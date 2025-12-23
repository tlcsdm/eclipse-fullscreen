# Fullscreen

With this small plugin Eclipse and RCP Programs based on Eclipse can run in full screen mode, so that you can have more space to edit your programs.

Now Support Eclipse 2024-06 and later, Mac OS X, Linux (with nautilus) and Windows.

## Use
1. Menu Window/Full Screen enter full screen mode.
2. Ctrl+ALT+Z enter or exit full screen mode in dialogs and windows.
3. ESC exit full screen mode in dialogs and windows.

![screenshot](https://raw.github.com/tlcsdm/eclipse-fullscreen/master/plugins/com.tlcsdm.eclipse.fullscreen/help/images/example.png)

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

1. Add `https://raw.githubusercontent.com/tlcsdm/eclipse-fullscreen/update_site/` as the upgrade location in Eclipse.
2. Download from [Jenkins](https://jenkins.tlcsdm.com/job/eclipse-plugin/job/eclipse-fullscreen)
3. <table style="border: none;">
  <tbody>
    <tr style="border:none;">
      <td style="vertical-align: middle; padding-top: 10px; border: none;">
        <a href='http://marketplace.eclipse.org/marketplace-client-intro?mpc_install=6998394' title='Drag and drop into a running Eclipse Indigo workspace to install eclipse-fullscreen'> 
          <img src='https://marketplace.eclipse.org/modules/custom/eclipsefdn/eclipsefdn_marketplace/images/btn-install.svg'/>
        </a>
      </td>
      <td style="vertical-align: middle; text-align: left; border: none;">
        ← Drag it to your eclipse workbench to install! (I recommand Main Toolbar as Drop Target)
      </td>
    </tr>
  </tbody>
</table>

