# doom-wad-metadata

This repository aims to contain all metadata for *Ultimate Doom*, *Doom 2*, *TNT:Evilution* and *The Plutonia
Experiment* episodes and levels in a computer readable format. When creating Doom related applications, Web sites
or something similar this should be the go-to repository for all level related metadata.

The data is provided in *EDN*, *JSON*, *YAML* and *XML* format. Most of it is taken from
[Doom Wiki](https://doom.fandom.com/wiki/Entryway).

## Usage

Take the data in the required format and use it for whatever project.

### doom-wad-metadata-tool

The [tool](./doom-wad-metadata-tool) is a mini Clojure project that contains `spec` that defines the structure of the
*iwad* data. `spec` can be used to check the validity of the data when some modifications are done. Also has functions
for transforming edn data to yaml and json and writing it to a file.

## ToDo

- [ ] Try to find missing par times.
- [ ] Complete map data statistics for Doom 2: Industrial Zone and TNT:Evilution: Administration Center levels.
- [ ] Add map statistics for things per difficulty level.
- [ ] XML transformation function in *doom-wad-metadata-tool*.

## License

Copyright © 2019 Dejan Josifović

Distributed under the GNU GENERAL PUBLIC LICENSE Version 3.
