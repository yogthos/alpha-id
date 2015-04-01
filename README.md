# alpha-id

A Clojure library for convering numbers into short alphanumeric IDs.

## Usage

The number is coverted to an alphanumeric string and back using the `encode` the `decode` functions respectively. The functions accept the dictionary as their first parameter and the value as the second. The default dictionaries are:

* `dict-16`
* `dict-32`
* `dict-64`
* `dict-89`

Using a larger dictionary results in a shorter ID being generated.

```clojure
(use 'alpha-id.core)

(encode dict-64 9007199254740992) ;=>"fFgnDxSe8"
(decode dict-64 "fFgnDxSe8") ;=> 9007199254740992

;;generate randomized tokens
(defn generate-token []
  (encode dict-32 (rand-int 10000000)))
  
(generate-token) ;=> "935VP"
```



## License

Copyright © 2015 Dmitri Sotnikov

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
