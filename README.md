# alpha-id

A Clojure library for convering numbers into short alphanumeric IDs.

[![Clojars Project](http://clojars.org/alpha-id/latest-version.svg)](http://clojars.org/alpha-id)

## Usage

The number is coverted to an alphanumeric string and back using the `encode` the `decode` functions respectively. The functions accept the dictionary as their first parameter and the value as the second.

Helper functions are provided for the following dictionary sizes:

* `encode-16`/`decode-16`
* `encode-32`/`decode-32`
* `encode-64`/`decode-64`
* `encode-89`/`decode-89`

Using a larger dictionary results in a shorter ID being generated.

```clojure
(use 'alpha-id.core)

(def dict [\0 \1 \2 \3 \4 \5 \6 \7 \8 \9 \A \B \C \D \E \F])
(encode dict 123456) ;=> "1E240"
(decode dict "1E240") ;=> 123456

(encode-64 9007199254740992) ;=>"fFgnDxSe8"
(decode-64 "fFgnDxSe8") ;=> 9007199254740992
```

## License

Copyright © 2015 Dmitri Sotnikov

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
