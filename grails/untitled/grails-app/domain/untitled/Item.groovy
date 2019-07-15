package untitled

class Item {

    String title
    String subtitle
    static belongsTo = [ categoty: Category ]
    int number
    String imageUrl

    static constraints = {
    }
    String toString(){
        return "{"+"title:"+title +"subtitle:"+subtitle+"}"
    }
}
