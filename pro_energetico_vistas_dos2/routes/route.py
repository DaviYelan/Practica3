from flask import Blueprint, abort, request, render_template, redirect
import json
import requests
from flask import flash
router = Blueprint('router', __name__)

@router.route('/')
def home():
    return render_template('template.html')

##Proyecto routes
@router.route('/listas/proyecto')
def listas_proyecto():
    r = requests.get('http://localhost:8099/api/proyecto/lista')
    data = r.json()
    return render_template('proyecto/proyectos.html', lista = data["data"])

## Guardar el proyecto 
@router.route('/proyecto/crear', methods=['POST'])
def crear_proyecto():
    hearders = {'Content-Type': 'application/json'}
    form = request.form

    dataF = {
        "nombre": form['nom'],
        "inversion": form['inver'],
        "tiempodevida": form['tiv'],
        "fechaInicio": form['fI'],
        "fechaFin": form['fF'],
        "electicidadGeneradapordia": form['egpd'],
        "acronimo": form['acr'],
        "costototal": form['ct'],
    }
    r = requests.post('http://localhost:8099/api/proyecto/guardar', headers=hearders, data=json.dumps(dataF))
    dat = r.json()
    if r.status_code == 200:
        flash("Se guardo correctamente", category='info')
        return redirect('/listas/proyecto')
    else:
        flash(str(dat["message"]), category='error')
        return redirect('/listas/proyecto')
    
##Eliminar el proyecto
@router.route('/proyecto/eliminar/<id>', methods=['DELETE'])
def eliminar_proyecto(id):
    headers = {'Content-Type': 'application/json'}
    r = requests.delete(f'http://localhost:8099/api/proyecto/eliminar/{id}', headers=headers)

    if r.status_code == 200:
        flash("Proyecto eliminado correctamente", category='info')
    else:
        dat = r.json()
        flash(str(dat.get("message", "Error al eliminar el proyecto")), category='error')
    
    return redirect('/listas/proyecto')

    
##Vista del guardar proyecto
@router.route('/proyecto/formulario')
def formulario_proyecto():
    return render_template('proyecto/guardarproyecto.html')

##llamar para editar el proyecto
@router.route('/proyecto/editar/<id>')
def editar_proyecto(id):
    r = requests.get('http://localhost:8099/api/proyecto/lista/'+id)
    data = r.json()
    if(r.status_code == 200):
        return render_template('proyecto/editarproyecto.html', lista = data["data"])
    else:
        flash(data["data"],"Error al cargar la pagina", category='error')
        return redirect('/listas/proyecto')  

##Editar el proyecto 
@router.route('/proyecto/editar', methods=['POST'])
def editarproyecto():
    hearders = {'Content-Type': 'application/json'}
    form = request.form

    dataF = {
        "idProyecto": form['id'],	
        "nombre": form['nom'],
        "inversion": form['inver'],
        "tiempodevida": form['tiv'],
        "fechaInicio": form['fI'],
        "fechaFin": form['fF'],
        "electicidadGeneradapordia": form['egpd'],
        "acronimo": form['acr'],
        "costototal": form['ct'],
    }
    r = requests.post('http://localhost:8099/api/proyecto/editar', headers=hearders, data=json.dumps(dataF))
    dat = r.json()
    if r.status_code == 200:
        flash("Se editado correctamente", category='info')
        return redirect('/listas/proyecto')
    else:
        flash(str(dat["message"]), category='error')
        return redirect('/listas/proyecto')
    
## Endpoint para listar las transacciones
@router.route('/transacciones')
def listas_transacciones():
    r = requests.get('http://localhost:8099/api/transaccion/lista')  # Asegúrate de que este sea el endpoint correcto
    data = r.json()
    
    return render_template('transaccion/transacciones.html', lista=data["data"])


## inversionista routes
## Obtener la lista de inversionistas relacionados con un proyecto

@router.route('/inversionista/listas/<acronimo>')
def listainversionistas(acronimo):
    r = requests.get('http://localhost:8099/api/inversionista/listas/'+acronimo)
    data = r.json()
    r = requests.get('http://localhost:8099/api/proyecto/lista')
    dataf = r.json()
    proyecto_seleccionado = next((proyecto for proyecto in dataf["data"] if proyecto["acronimo"] == acronimo), None)
    return render_template('inversionista/inversionistas.html', lista = data["data"], listaf = dataf["data"], proyecto = proyecto_seleccionado)

@router.route('/inversionista/formulario/<acronimo>')
def formulario_asociar_inversionista(acronimo):
    r = requests.get('http://localhost:8099/api/inversionista/listas/'+acronimo)
    data = r.json()
    r = requests.get('http://localhost:8099/api/proyecto/lista')
    dataf = r.json()

    proyecto_seleccionado = next((proyecto for proyecto in dataf["data"] if proyecto["acronimo"] == acronimo), None)
    return render_template('inversionista/guardarinversionista.html', lista  = data["data"], listaf = dataf["data"], proyecto = proyecto_seleccionado)

@router.route('/inversionista/<acronimo>')
def inversionistas(acronimo):
    # Obtener lista de inversionistas relacionados con el proyecto
    r = requests.get('http://localhost:8099/api/inversionista/listas/'+acronimo)
    data = r.json()

    # Obtener la lista de proyectos y filtrar el proyecto seleccionado
    r = requests.get('http://localhost:8099/api/proyecto/lista')
    dataf = r.json()
    
    # Filtrar el proyecto por acrónimo
    proyecto_seleccionado = next((proyecto for proyecto in dataf["data"] if proyecto["acronimo"] == acronimo), None)
    return render_template('templateinversion.html', lista = data["data"], listaf = dataf["data"], proyecto = proyecto_seleccionado)

## Asociar un inversionista a un proyecto
@router.route('/inversionista/asociar/<acronimo>', methods=['POST'])
def asociar_inversionista(acronimo):
    headers = {'Content-Type': 'application/json'}
    form = request.form

    dataF = {
        "nombre": form['nombre'],
        "apellido": form['apellido'],
        "dni": form['dni'],
        "telefono": form['telefono'],
    }
    
    r = requests.post('http://localhost:8099/api/inversionista/asociar/'+acronimo, headers=headers, data=json.dumps(dataF))
    dat = r.json()
    
    if r.status_code == 200:
        flash("Inversionista asociado correctamente", category='info')
        return redirect('/inversionista/listas/'+acronimo)
    else:
        flash(str(dat["msg"]), category='error')
        return redirect('/inversionista/listas/'+acronimo)




## Practica_3 ################################################################################################

@router.route('/proyecto/buscar', methods=['POST'])
def buscar_proyectos():
    headers = {'Content-Type': 'application/json'}
    form = request.form

    dataF = {
        "criterio": form['criterio'],
        "atributo": form['atributo'],
        "metodoBusqueda": form['metodoBusqueda']
    }
    
    r = requests.post('http://localhost:8099/api/proyecto/buscar', headers=headers, data=json.dumps(dataF))
    dat = r.json()
    
    if r.status_code == 200:
        return render_template('proyecto/proyectos.html', lista=dat["data"])
    else:
        flash(str(dat["data"]), category='error')
        return redirect('/listas/proyecto')

@router.route('/proyecto/ordenar', methods=['POST'])
def ordenar_proyectos():
    headers = {'Content-Type': 'application/json'}
    form = request.form

    dataF = {
        "atributo": form['atributo'],
        "algoritmo": form['algoritmo'],
        "ascendente": form['ascendente']
    }
    
    r = requests.post('http://localhost:8099/api/proyecto/ordenar', headers=headers, data=json.dumps(dataF))
    dat = r.json()
    
    if r.status_code == 200:
        return render_template('proyecto/proyectos.html', lista=dat["data"])
    else:
        flash(str(dat["data"]), category='error')
        return redirect('/listas/proyecto')