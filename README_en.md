# jexpr-encoder-utils

<p>
  <img title="portainer" src='https://img.shields.io/github/v/release/woodpecker-appstore/jexpr-encoder-utils?include_prereleases' />
  <img title="portainer" src='https://img.shields.io/badge/woodpecker-1.3.x-yellow.svg' />
  <img title="portainer" src='https://img.shields.io/badge/license-MIT-red.svg' />
</p>

Java expression statement builder.

[简体中文](README.md) | English

---

## 0x01 Introduction
`jexpr-encoder-utils`is a plug-in for generating various java language expression statements in [woodpecker-framework](https://github.com/woodpecker-framework/woodpecker-framwork-release/releases).


## 0x02 Supported expressions


| Expression Name         | Command execution | Command execution with result | DNSLog | HTTPLog | Sleep | MemShell Inject | JNDI | Load Jar |
|-------------------------|-------------------|-------------------------------|--------|---------|-------|-----------------|------|----------|
| SpEL                    | √                 | √                             | √      | √       | √     | √               | √    | x        |
| OGNL                    | √                 | √                             | √      | √       | √     | √               | √    | x        |
| FreeMarker              | √                 | √                             | √      | √       | √     | √               | √    | x        |
| EL                      | √                 | √                             | √      | √       | √     | √               | √    | x        |
| Velocity                | √                 | √                             | √      | √       | √     | √               | x    | x        |
| ScriptEngineManager(JS) | √                 | √                             | √      | √       | √     | √               | √    | √        |

## 0x03 Screenshot

![](./images/screenshot_01.png)

## 0x04 Contributor

[whwlsfb](https://github.com/whwlsfb/)@SgLab

<img src="./images/sglab.svg" width=300 alt="SgLab">