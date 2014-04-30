(def dim 512)
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

(defn m []
    (apply vector
           (map (fn [_]
                  (apply vector (map (fn [_] (+ 1 (rand 2)))
                                     (range dim))))
                (range dim))))

(def soln
     (apply vector
            (map (fn [_]
                   (apply vector (map (fn [_] 0)
                                      (range dim))))
                 (range dim))))

(def soln-atom
     (apply vector
            (pmap (fn [_]
                   (apply vector (pmap (fn [_] (atom 0))
                                      (range dim))))
                 (range dim))))

(defn print-matrix [m] (dotimes [i dim] (print (nth m i) "\n")))

(defn print-soln-matrix [m] (dotimes [i dim] (print (map #(deref %) (nth m i)) "\n")))

(defn mult-rc [a ai b bi] (reduce + (map * (nth a ai) (nth b bi))))

(defn get-elem [ai bi m] (nth (nth m ai) bi))

(comment
(defn mult-matrix [] 
  (dotimes [i dim] 
    (dotimes [j dim] 
      (cond (and i j) 
        (send-off (get-elem i j soln) + (mult-rc i j))))))
)

(defn mult-matrix-atom [a b] 
  (dotimes [i dim] 
    (dotimes [j dim]
      (cond (and i j) 
        (swap! (get-elem i j soln-atom) + (mult-rc a i b j))))))

(comment
(defn mult-matrix-atom-p [] 
  (dotimes [i dim]  
    (dotimes [j dim] 
      (cond (and i j) 
        (print (mult-rc i j) "\n")
        (print i "\n")
        (print j "\n")))))

(defn mult-matrix-no-c [] (dotimes [i dim] (dotimes [j dim] (cond (and i j) (mult-rc i j)))))
)


;(defn update [] nil)
;(defn update [ai bi] (print ai bi))

(defn show-mult [] (for [i (range dim)] (for [j (range dim)] (print (get-elem i j) (mult-rc i j)))))

;(time (get-mult))
;(print soln)


;(time (pcalls (time mult-matrix-atom) (time mult-matrix-atom) (time mult-matrix-atom)))
;(time (dotimes [n 100] (time mult-matrix-no-c)))

;(time (pcalls (dotimes [_ 100]  (time (mult-matrix-atom)) (print-matrix soln-atom))))

(comment
(print "mult-matrix-atom concurrent\n\n")

(time (dotimes [i 1]
  (let [m (m)]
  (.start (Thread. #(do

                     (mult-matrix-atom m m)
                     ;(print (reduce + (map #(deref %) (first soln-atom))) "\n")
                     ;(print-soln-matrix soln-atom)
                     ))))))

(print "\n")
)

(comment
(time (dotimes [i 120]
  (let [m (m)]
   (do
  (mult-matrix-atom m m)
  ;(print (reduce + (map #(deref %) (first soln-atom))) "\n")
  ;(print-soln-matrix soln-atom)
))))
)

;(print-matrix soln)
;(send (get-elem 0 0) + (mult-rc 0 0))


;(defn columnize [i] (for [n b] (nth n i)))

;(into [] (for [i (range dim)] (into [] (columnize i))))
