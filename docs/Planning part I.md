# Planning part I

## CLI

The first phase of the CLI is a menu selecting previous generated phase I file or to specify the arguments to generate a new one.
The user can also choose whether remove a previously generated file.

Once the file was selected or generated, the phase II begins...

## Components

### Simulator

This must run an operation over matrices.
And should notify a subscriber over the accesses to memory.

The constructor parameters should be rowSize, columnSize and wordSize.

### Page Reference Generator

The generator follows the logic of a page system.

It takes into account the page size and the page count.

The main function is a request which takes metadata and the wordsize.

The metadata is a record identifying the matrix, and the row and column being accessed.

## Overview

![Phase I overview](./PhaseI.png)