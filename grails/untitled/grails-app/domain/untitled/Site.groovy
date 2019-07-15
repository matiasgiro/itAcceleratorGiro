package untitled

class Site {

    String name
    static hasMany = [ categories: Category ]


    static constraints = {
    }

    String toString(){
        name
    }
}
