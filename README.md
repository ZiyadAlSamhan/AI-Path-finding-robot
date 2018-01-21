**\# Path finding Robot**

This project is part of CSC-361 (Artificial intelligence) course in King Saud University   


**\#Introduction**

A big and important part of our daily life is finding possible solution
or paths for out problems, we try to find a way that’s moves us from our
current state to another based on many factors.

Path finding s about moving from point A to point B, while considering
the obstacles that’s may make some paths has a dead end without any
possible way that could moves us to our target.

This project tries to formulate the problem on the computer making it
possible to be solved with them, it will contain a map locating a robot
with some empty cells allowing the robot to move on them, many
obstacles, holes, charge stations and a goal.

The robot will be allowed to move in for directions to up, down, right,
left with respect to the properties of the environment (for example it
cannot move to an obstacle cell), and a recharge action, the recharge
action can’ not be executed unless the robot in a charge station cell.

The robot has a battery charged with an initial variable, the battery
decreases each time when a robot move is executed.

There will be three algorithms to be implemented in this project:

-   Breadth First Search (BFS)

-   A\*

-   Hill-climbing (HC)

Input/output data will be stored in files.

**\#Problem description**

Path finding problems have many difficulties representing the problem in
the computer require a good modelling of the map and the objects inside
it, if the model wasn’t good it may not be possible to be represent in
the computer.

-There are memory limitations that’s need to be considered, data should
be stored in a good way.

-There is cost limitations, recourses should be chosen while considering
the cost aspect.

-There is time limit, it wouldn’t be efficient if the required process
takes a long time.

**\#Modelling**

**State:**

**M**: (rows) **Variables:** 1, 2, …, \*

**N**: (columns) **Variables:** 1, 2, …, \*

**X**: (Robot position in x-axe) **Variables:**1, 2, …, M

**Y**: (Robot position in y-axe) **Variables:**1, 2, …, N

**Battery**: (The battery state) **Variables:**1, 2, …, (N+M)

**isHole**: (Check whether the robot in a hole or not) **Variables:**
True/False

**TreasuresNum**:(Treasures counter) **Variables:** 1, 2, …, (M\*N)

**T\_X\[\]**: (Treasures position in x-axe) **Variables:** 1, 2, …, M

**T\_Y\[\]**: (Treasures position in y-axe) **Variables:** 1, 2, …, N

**MAP** \[ \]\[ \]: (The map) **Variables:** “ “(Empty cell)/
“R”(Robot)/ ”T”(Treasure)/ “B”( obstacles)/ “H”(Hole)/ “U”( Empty
cell-Robot-Treasure)/ ”X“(Hole-Robot)/ “Y”(Hole-Treasure)/
“Z”(Hols-Robot-Treasure)/ “C”(Charge station)/ “D”(Charge
station-Robot)/ “E”(Charge station-Treasure)/ “F”( Charge
station-Robot-Treasure)

**Initial state:**

X:0

Y:0

isHole: false

T\_X: {5}

T\_Y: {5}

MAP={

{R,B,H,B,E,B},

{E,B,E,E,E,C},

{E,B,E,B,E,B},

{E,E,E,B,E,H},

{E,B,B,B,E,B},

{C,E,H,B,E,T}

}

M:6

N:6

**Actions:**

Move\_n (Moving the robot north side up)

Move\_s (Moving the robot south side down)

Move\_w (Moving the robot west side left)

Move\_e (Moving the robot east side right)

Recharge (Allowing the robot to recharge his battery)

**Transition model:**

**Move\_n **

Preconditions: Y != 0 AND MAP\[Y-1\]\[x\] != “B” AND isHole==false AND
battery&gt;0

Result: Y=Y-1

If(MAP\[Y\]\[X\] == “H” OR MAP\[Y\]\[X\] == “Y”)

isHole=true

battery-1

**Move\_s **

Preconditions: Y != M AND MAP\[Y+1\]\[x\] != “B” AND isHole==false AND
battery&gt;0

Result: Y=Y+1

If(MAP\[Y\]\[X\] == “H” OR MAP\[Y\]\[X\] == “Y”)

isHole=true

battery-1

**Move\_w **

Preconditions: X != 0 AND MAP\[Y\]\[x-1\] != “B” AND isHole == false AND
battery&gt;0

Result: X=X-1

If(MAP\[Y\]\[X\] == “H” OR MAP\[Y\]\[X\] == “Y”)

isHole=true

> battery-1

**Move\_e **

Preconditions: X != N AND MAP\[Y\]\[x+1\] != “B” AND isHole == false AND
battery&gt;0

Result: X=X-1

If(MAP\[Y\]\[X\] == “H” OR MAP\[Y\]\[X\] == “Y”)

isHole=true

battery-1

**Recharge **

Preconditions: MAP\[Y\]\[X\] == “C” OR MAP\[Y\]\[X\] == “D” OR

> MAP\[Y\]\[X\] == “E” OR MAP\[Y\]\[X\] == “F”

Result: battery=M+N

**Goal test:**

Loop var TreasureNum tims

> If (X == T\_X\[var\] AND Y == T\_Y\[var\])

true

**Path cost:**

Move\_n (1)

Move\_s (1)

Move\_w (1)

Move\_e (1)

Recharge (1)

**\#Algorithms & heuristics**

-   Breadth First Search (BFS)

> In BFS searching algorithm we explore all possible actions till we
> find the treasure, the explored nodes will be stored (fringe) in a
> queue data structure.

-   A\*

> In A\* searching algorithm we explore the nodes to find the optimal
> path we start evaluating the function by Manhattan distance heuristic
> in addition to the path cost value.
>
> The treasure will be find with an optimal solution, the explored nodes
> will be stored (fringe) in a priority queue data structure.

-   **Hill-climbing (HC)**

> In HC algorithm, we explore the nodes to and start evaluating the
> neighbors of each node and select the most value we evaluate the node
> using an objective function work as same as Manhattan distance in.


**\#Discussion**

The biggest difficulty that I faced in this project is considering large
maps file as an input, I tried to give the program a map with
\[25\]\[25\] size and it didn’t show any output for more than an hour
witch have made me to double check my code and finally I start to
realized that with the given heuristic it is hard to find the solution.

I tried to make some additional constrains to solve \[25\]\[25\] map by
allowing the robot to go to a specific cell twice regardless the battery
value and it didn’t show any progress in fact it have shown an exception

**\#Summery**

We can formulate a problem with all its properties. we have successfully
made a complete map representation with all its properties (obstacles
-Robot-holes- etc.. ) in the computer giving an opportunity to find the
best possible path from point to another if possible.

The main problem was finding the best solution with respect to time and
space we have seen that some each algorithm spatialized in one aspect

The following result is based on three different sizes of map as an
input.

We can obviously see the BFS is the slowest algorithm then we will find
the A\* and HC are similar to each other in small inputs depending on
the problem difficulty.

We ***can*** see that A\* and BFS could have the same path costs
although they have different explored nodes.

We also see BFS is the most expanding algorithm then we will find the
A\* after it and finally HC.

The represent charts comparison above is not the general case since
there are many factors have not been consider for example(How many
treasures in the map, how many hole,…).

I would suggest using A\* in general since with a better heuristic it
has a good run time limit, reasonable reason number of expanded nodes
and an optimal path cost, this suggestion based on personal
presentations.


