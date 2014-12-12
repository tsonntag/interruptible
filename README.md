interruptible
=============

Clojure library for handling interrupts (ctrl-c) gracefully

## Installation

**interruptible** is available through [Clojars](http://clojars.org/interruptible).

For the latest release, in Leiningen, add to `project.clj`:

```clojure
:dependencies [[interruptible "0.1.0"]]
```

## Usage

```clojure
(use '[interruptible.core])

(interruptible f <args...>) 
```

calls `(f interrupted? <args...>)` catching interrupts (e.g. ctrl-c)

where `interrupted?`  is a function which returns true if an interrupt (e.g. ctrl-c) has occured.
f may call `interrupted?` to interrupt its work gracefully:

```clojure
(defn f [interrupted? & args]
  ; do some work ...
  ; check if we shall continue
  (when-not (interrupted?)
     ; continue with some more work... 
     ))
```


```clojure
(use '[interruptible.core])

(infinitely f <msecs> <args....>) 
```

calls `(f interrupted? <args...>)` infinitely sleeping msecs milliseconds between each call
until an interrupt (e.g. crtl-c) occurs.

`interrupted?` is a function as described above.

## License

Copyright © 2014 Thomas Sonntag

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
