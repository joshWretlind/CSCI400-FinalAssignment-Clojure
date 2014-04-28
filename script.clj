(def dim 3)
(def r #(rand-int %))

(def m (into [] (repeat row (into [] (repeat col (atom []))))))


(def a
     (apply vector
            (map (fn [_]
                   (apply vector (map (fn [_] (rand-int 10))
                                      (range dim))))
                 (range dim))))

(def b
    (apply vector
           (map (fn [_]
                  (apply vector (map (fn [_] (rand-int 10))
                                     (range dim))))
                (range dim))))

(def soln
     (apply vector
            (map (fn [_]
                   (apply vector (map (fn [_] (agent (0))
                                      (range dim))))
                 (range dim))))

(defn mult-rc [ai bi] (reduce + (map * (nth a ai) (nth b bi))))



(defn columnize [i] (for [n b] (nth n i)))

(into [] (for [i (range dim)] (into [] (columnize i))))
