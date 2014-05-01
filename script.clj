(def dim 512)
(def numOfThreads 8)

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

(defn mult-matrix-atom [a b] 
  (dotimes [i dim] 
    (dotimes [j dim]
      (cond (and i j) 
        (swap! (get-elem i j soln-atom) + (mult-rc a i b j))))))

(defn show-mult [] 
  (for [i (range dim)] 
    (for [j (range dim)] 
      (print (get-elem i j) (mult-rc i j)))))

(def startTime (System/currentTimeMillis))
;(comment
(print "mult-matrix-atom concurrent\n\n")
(time (dotimes [i numOfThreads]
  (let [m (m)]
  (.start (Thread. #(do
                     
                     (mult-matrix-atom m m)
                     ;(print (reduce + (map #(deref %) (first soln-atom))) "\n")
                     ;(print-soln-matrix soln-atom)
                     (def endTime (System/currentTimeMillis))
                     (print (str "Thread #" i " took " (- endTime startTime) " ms to run \n"))
                     ))))))
(print "\n")
;)


;(comment
(print "mult-matrix-atom NOT concurrent\n\n")
(time (dotimes [i numOfThreads]
  (let [m (m)]
   (do
  (mult-matrix-atom m m)
  ;(print (reduce + (map #(deref %) (first soln-atom))) "\n")
  ;(print-soln-matrix soln-atom)
))))
(print "\n")
;)
