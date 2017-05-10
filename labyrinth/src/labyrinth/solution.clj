(ns labyrinth.solution)

(def walkable? #{:_ :Exit})
(defn can-go? [direction lab pos]
  (walkable? (get-in lab (direction pos))))
(defn up [[x y]]
  [x (dec y)])
(defn down [[x y]]
  [x (inc y)])
(defn left [[x y]]
  [(dec x) y])
(defn right [[x y]]
  [(inc x) y])

(defn walk [lab pos]
  (->> [up down left right]
       (filter #(can-go? % lab pos))
       (mapcat #(solutions-at lab (% pos)))))

(defn solutions-at [lab pos]
  (if (= :Exit (get-in lab pos))
    [lab]
    (walk (assoc-in lab pos 1) pos)))

(defn solve [lab]
  (let [solutions (solutions-at lab [0 0])]
    (if (empty? solutions) ["no way out"]
        (first solutions))))

(solutions-at [[1  :W :Exit]
               [:_ :_ :_]
               [:_ :W :_]
               [:_ :_ :_]] [0 0])

(comment "Doesn't find the starting position. Assumes [0 0]"
         "Doesn't mark :Exit with 1"
         "solutions-at returns a lazy sequence of all solutions") 

