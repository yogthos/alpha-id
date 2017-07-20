(ns alpha-id.core)

(def ^:private dict-16 [\0 \1 \2 \3 \4 \5 \6 \7 \8 \9 \A \B \C \D \E \F])

(def ^:private dict-32 [\0 \1 \2 \3 \4 \5 \6 \7 \8 \9 \A \B \C \D \E \F \G \H \J \K \M \N \P \Q \R \S \T \U \V \W \X \Y \Z])

(def ^:private dict-64 [\0 \1 \2 \3 \4 \5 \6 \7 \8 \9 \A \B \C \D \E \F \G \H \I \J \K \L \M \N \O \P \Q \R \S \T \U \V \W \X \Y \Z \a \b \c \d \e \f \g \h \i \j \k \l \m \n \o \p \q \r \s \t \u \v \w \x \y \z])

(def ^:private dict-89 [\0 \1 \2 \3 \4 \5 \6 \7 \8 \9 \A \B \C \D \E \F \G \H \I \J \K \L \M \N \O \P \Q \R \S \T \U \V \W \X \Y \Z \a \b \c \d \e \f \g \h \i \j \k \l \m \n \o \p \q \r \s \t \u \v \w \x \y \z \+ \" \@ \* \# \% \& \/ \| \( \) \= \? \~ \[ \] \[ \} \$ \- \_ \. \: \space \, \; \< \>])

#?(:clj
   (def ^:private memoized-dict-map
     (fn [dict]
       (into {} (map-indexed (fn [i c] [c (BigInteger. (str i))]) dict))))
   :cljs
   (def ^:private memoized-dict-map
     (fn [dict]
       (into {} (map-indexed (fn [i c] [c (js/parseInt (str i))]) dict)))))

#?(:clj
   (defn encode [dict value]
     (let [base (-> dict count str BigInteger.)]
       (loop [result ()
              remaining (-> value str BigInteger.)
              exponent 1]
         (if (.equals remaining BigInteger/ZERO)
           (apply str result)
           (let [a (.pow base exponent)
                 b (.mod remaining a)
                 c (.pow base (dec exponent))
                 d (.divide b c)]
             (recur (cons (dict (.intValue d)) result)
                    (.subtract remaining b)
                    (inc exponent)))))))
   :cljs
   (defn encode [dict value]
     (let [base (-> dict count str js/parseInt)]
       (loop [result ()
              remaining (-> value str js/parseInt)
              exponent 1]
         (if (== remaining 0)
           (apply str result)
           (let [a (js/Math.pow base exponent)
                 b (mod remaining a)
                 c (js/Math.pow base (dec exponent))
                 d (/ b c)]
             (recur (cons (dict (int d)) result)
                    (- remaining b)
                    (inc exponent))))))))

#?(:clj
   (defn decode [dict value]
     (let [chars (reverse value)
           base (-> dict count str BigInteger.)
           dict-map (memoized-dict-map dict)]
       (loop [bi BigInteger/ZERO
              exponent 0
              [c & remaining] chars]
         (if c
           (let [a (dict-map c)
                 b (.multiply (.pow base exponent) a)]
             (recur (.add bi (BigInteger. (str b)))
                    (inc exponent)
                    remaining))
           bi))))
   :cljs
   (defn decode [dict value]
     (let [chars (reverse value)
           base (-> dict count str js/parseInt)
           dict-map (memoized-dict-map dict)]
       (loop [bi 0
              exponent 0
              [c & remaining] chars]
         (if c
           (let [a (dict-map c)
                 b (* (js/Math.pow base exponent) a)]
             (recur (+ bi (js/parseInt (str b)))
                    (inc exponent)
                    remaining))
           bi)))))

(defn encode-16 [value]
  (encode dict-16 value))

(defn decode-16 [value]
  (decode dict-16 value))

(defn encode-32 [value]
  (encode dict-32 value))

(defn decode-32 [value]
  (decode dict-32 value))

(defn encode-64 [value]
  (encode dict-64 value))

(defn decode-64 [value]
  (decode dict-64 value))

(defn encode-89 [value]
  (encode dict-89 value))

(defn decode-89 [value]
  (decode dict-89 value))
