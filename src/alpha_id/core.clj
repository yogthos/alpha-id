(ns alpha-id.core)

(def dict-16 [\0 \1 \2 \3 \4 \5 \6 \7 \8 \9 \A \B \C \D \E \F])
 
(def dict-32 [\1 \2 \3 \4 \5 \6 \7 \8 \9 \A \B \C \D \E \F \G \H \J \K \M \N \P \Q \R \S \T \U \V \W \X \Y \Z])
 
(def dict-64 [\0 \1 \2 \3 \4 \5 \6 \7 \8 \9 \A \B \C \D \E \F \G \H \I \J \K \L \M \N \O \P \Q \R \S \T \U \V \W \X \Y \Z \a \b \c \d \e \f \g \h \i \j \k \l \m \n \o \p \q \r \s \t \u \v \w \x \y \z])
 
(def dict-89 [\0 \1 \2 \3 \4 \5 \6 \7 \8 \9 \A \B \C \D \E \F \G \H \I \J \K \L \M \N \O \P \Q \R \S \T \U \V \W \X \Y \Z \a \b \c \d \e \f \g \h \i \j \k \l \m \n \o \p \q \r \s \t \u \v \w \x \y \z \+ \" \@ \* \# \% \& \/ \| \( \) \= \? \~ \[ \] \[ \} \$ \- \_ \. \: \space \, \; \< \>])
 
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

(def ^:private memoized-dict-map
  (fn [dict]
    (into {} (map-indexed (fn [i c] [c (BigInteger. (str i))]) dict)))) 
 
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

