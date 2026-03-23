# MinecartFilter

Stops minecart lag by limiting how many can exist on a rail network or within a chunk.

## Setup

Drop the jar in your `plugins/` folder and restart. That's it.

## Config

```yaml
max-minecarts-per-rail: 10   # limit per connected rail network
max-minecarts-per-chunk: 20  # limit per chunk (checked first)

message-rail-limit: "&cToo many minecarts on this rail! Maximum is {limit}."
message-chunk-limit: "&cToo many minecarts in this chunk! Maximum is {limit}."

log-violations: false
```

## Commands

`/minecartfilter` — show current limits  
`/minecartfilter reload` — reload config

## Permissions

| Permission | Default |
|---|---|
| `minecartfilter.admin` | OP |
| `minecartfilter.bypass` | false |

Players with `minecartfilter.bypass` are exempt from all limits.

## Requirements

Spigot / Paper 1.20+, Java 17+
