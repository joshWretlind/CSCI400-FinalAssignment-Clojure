(import java.util.concurrent.Executors)

(def custom-pool (Executors/newFixedThreadPool 64))
;; just like clojure.core/send but will use custom-pool instead
;; of an internally maintained one

(def dim 64)
(def r #(rand-int %))

;(def m (into [] (repeat row (into [] (repeat col (atom []))))))

(def a
     (apply vector
            (map (fn [_]
                   (apply vector (map (fn [_] (rand 10))
                                      (range dim))))
                 (range dim))))

(def b
    (apply vector
           (map (fn [_]
                  (apply vector (map (fn [_] (rand 10))
                                     (range dim))))
                (range dim))))

(def soln
     (apply vector
            (map (fn [_]
                   (apply vector (map (fn [_] (agent 0))
                                      (range dim))))
                 (range dim))))

(defn print-matrix [m] (dotimes [i dim] (print (nth m i) "\n")))
(defn print-soln-matrix [m] (dotimes [i dim] (print (nth m i) "\n")))

;(print-matrix a)
(print "\n")
;(print-matrix b)
(print "\n")
;(print-matrix soln)
(print "\n")

(defn mult-rc [ai bi] (reduce + (map * (nth a ai) (nth b bi))))

(defn get-elem [ai bi] (nth (nth soln ai) bi))

(defn mult-matrix [] (dotimes [i dim] (dotimes [j dim] (cond (and i j) (send-via custom-pool (get-elem i j) + (mult-rc i j))))))

;(defn update [] nil)
;(defn update [ai bi] (print ai bi))

(defn show-mult [] (for [i (range dim)] (for [j (range dim)] (print (get-elem i j) (mult-rc i j)))))

(time (mult-matrix))
(time (mult-matrix))
(time (mult-matrix))
(time (mult-matrix))
(time (mult-matrix))
(time (mult-matrix))
(time (mult-matrix))
(time (mult-matrix))
(time (mult-matrix))
(time (mult-matrix))
(time (mult-matrix))
(time (mult-matrix))
(time (mult-matrix))
(time (mult-matrix))
(time (mult-matrix))
(time (mult-matrix))

;(print-matrix soln)
(print "\n")
;(send (get-elem 0 0) + (mult-rc 0 0))


;(defn columnize [i] (for [n b] (nth n i)))

;(into [] (for [i (range dim)] (into [] (columnize i))))
