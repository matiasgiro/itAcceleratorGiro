package untitled

class Category {

    String name
    static belongsTo = [ site: Site ]
    static hasMany = [ items: Item ]


    static constraints = {

    }
    String toString(){
        name
    }
}
