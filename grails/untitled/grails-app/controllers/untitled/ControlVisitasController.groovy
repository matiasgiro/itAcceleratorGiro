package untitled

class ControlVisitasController {

    def index() {
        def lista = []


        Item.getAll().each {valor ->
            lista.add(valor)
        }

        [lista:lista]

    }

    def saludo(){

        render ('view':'index.gsp')
    }

    def displayForm(){
        Persona persona = new Persona()
        [persona:persona]
    }

    def save(){
        def a = params
        render ('view':'displayItem.gsp', title:a.title, subtitle:a.subtitle, category:a.category)    }

    def show(){
        Persona persona = Persona.get(1)
        render persona.nombre + " - "+ persona.apellido + " - " + persona.edad
    }
}
