(ns labyrinth.solution)

(def walkable? #{:_ :Exit})

(defn can-go? [direction maze pos]
  (walkable? (get-in maze (direction pos))))

(defn vec+ [& vs]
  (apply (partial map +) vs))

(defn up [pos] (vec+ pos [0 -1]))
(defn down [pos] (vec+ pos [0 1]))
(defn left [pos] (vec+ pos [-1 0]))
(defn right [pos] (vec+ pos [1 0]))

(declare solutions-at)

(defn walk [maze pos]
  (->> [up down left right]
       (filter #(can-go? % maze pos))
       (mapcat #(solutions-at maze (% pos)))))

(defn solutions-at [maze pos]
  (if (= :Exit (get-in maze pos))
    [maze]
    (walk (assoc-in maze pos 1) pos)))

(defn solve [maze]
  (let [solutions (solutions-at maze [0 0])]
    (if (empty? solutions) ["no way out"]
        (first solutions))))

(solutions-at [[1 :W :_ :_ :_]
               [:_ :W :_ :W :_]
               [:_ :_ :_ :W :Exit]
               [:_ :W :_ :_ :_]] [0 0])
