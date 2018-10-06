World/Environment:
    gravity
    resistance
    other constants
    
    Surface?
    
    Cities
        Population/Health

    Launch Platform:
        health
        max speed
        fuel
    
        Launchers:
            number of Missiles
            health
            Missiles:
                Attached Warheads:
                    number of warheads
                    Warhead type:
                        detonation on hit probability
                        Damage
                        weight

                Rocket motor:
                    amount of fuel
                    fuel consumption
                    force
                    force range
                    manuverability
                
                Targeting:
                    target (coords/object)


Physical Object:
    position
    velocity
    weight
    health
    angle

Explosion
    Damage
    current radius
    expansion rate 
    max radius


All objects are either free or stored in another object:
    if stored in another object all top level object effect affect lower levels e.g. damage/move/collide/accelerate
    If an object is launched e.g. missile/warhead it becomes a top level object
    If any object is damaged it damages the subcomponents to, if a warhead is damaged it may detonate, detonating warheads are made top level objects
            
            
    setKinetics is the inactive version of move, the caller of setKinetics is in control. 

Angles:
    [pitch, yaw, roll]/[up/down, left/right, roll]
    


            Description:
                describes the entity and it's children
                type
                position
                velocity
                angle
                mass
                health
                children

    manuverability:
        Only defined as a limit, no acceleration.
        Applied on a per Entity basis
        the larger the mass of the Entity the slower the maximum rotation rate.
        Defined for all 3 angles.
        Excess rotation commands will be limited to the maximum.
        Rotation commands will be run with the speed of rotation in rads per second.



Viewer:
    takes information or state (A world object) and draws this information;
    Either:
        take a world object and extract each object and render individually
        or
        convert all world objects into descriptive objects (i.e. type, location, children etc. ) in a tree

    Convert coordinates into screen coordiantes.



TASK LIST:  
    - [x] Get warheads to detonate on command:  
        Warheads now use targeting to detonate  
    - [x] Allow rotation of Entities:  
        - [x] Add rotationRateLimit  
        - [ ] ???  
        - [x] Add targeting control of rotation  
    - [X] @Override stuff    
    - [ ] update Targeting    
    - [ ] build renderer  
    

