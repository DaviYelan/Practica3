package controller.tda.list;

import java.lang.reflect.Method;

import controller.tda.list.LinkedList;

public class LinkedList<E> {
    private Node<E> header; // Nodo cabecera (el primer nodo de la lista)
    private Node<E> last; // Nodo último (el último nodo de la lista)
    private Integer size; // Tamaño de la lista (cuenta el número de nodos en la lista)

    // Constructor de la clase LinkedList
    public LinkedList() {
        this.header = null; // Inicialmente, la cabecera es nula (no hay nodos en la lista)
        this.last = null; // Inicialmente, el último nodo es nulo
        this.size = 0; // Inicialmente, el tamaño de la lista es 0
    }

    // Método para verificar si la lista está vacía
    public Boolean isEmpty() {
        // Retorna verdadero si la cabecera es nula o el tamaño es 0, es decir, si la
        // lista está vacía
        return (this.header == null || this.size == 0);
    }

    // Método privado para agregar un elemento al principio de la lista
    private void addHeader(E dato) {
        Node<E> help; // Nodo de ayuda para insertar el nuevo dato

        // Si la lista está vacía
        if (isEmpty()) {
            help = new Node<>(dato); // Crea un nuevo nodo con el dato
            header = help; // El nuevo nodo se convierte en el nodo cabecera
            this.size++; // Incrementa el tamaño de la lista
        } else {
            // Si la lista no está vacía
            Node<E> healpHeader = this.header; // Guarda el nodo cabecera actual en una variable auxiliar
            help = new Node<>(dato, healpHeader); // Crea un nuevo nodo que apunta al nodo cabecera actual
            this.header = help; // El nuevo nodo se convierte en la nueva cabecera
        }
        this.size++; // Incrementa el tamaño de la lista
    }

    private void addLast(E info) {
        Node<E> help; // Nodo para ayudar a agregar el nuevo elemento
        if (isEmpty()) { // Verificar si la lista está vacía
            help = new Node<>(info); // Crear un nuevo nodo
            header = help; // Establecer el nuevo nodo como cabecera
            last = help; // Establecer el nuevo nodo como último
        } else {
            help = new Node<>(info, null); // Crear un nuevo nodo
            last.setNext(help); // Conectar el último nodo al nuevo nodo
            last = help; // Actualizar 'last' al nuevo nodo
        }
        this.size++; // Incrementar el tamaño de la lista
    }

    public void add(E info) {
        addLast(info);
    }

    private Node<E> getNode(Integer index) throws ListEmptyException, IndexOutOfBoundsException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, List empty");
        } else if (index.intValue() < 0 || index.intValue() >= this.size) {
            throw new IndexOutOfBoundsException("Error, fuera de rango");
        } else if (index.intValue() == (this.size - 1)) {
            return last;
        } else {
            Node<E> search = header;
            int cont = 0;
            while (cont < index.intValue()) {
                cont++;
                search = search.getNext();
            }
            return search;
        }
    }


  
    public void update(E data, Integer post) throws ListEmptyException {
        if(isEmpty()) {
            throw new ListEmptyException("Error, la lista esta vacia");
        } else if(post < 0 || post >= size) {
            throw new IndexOutOfBoundsException("Error, fuera de rango");
        } else if(post == 0) {
            header.setInfo(data);
        } else if(post == (this.size - 1)) {
            last.setInfo(data);
        } else {
            Node<E> search = getNode(post);
            Integer cont = 0;
            while(cont < post) {
                cont++;
                search = search.getNext();
            }
            search.setInfo(data);
        }

    }
    

    //borrar la cabezera
    public E deleteFirst() throws ListEmptyException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, lista vacia");
        } else {
            E element = header.getInfo();
            Node<E>  aux = header.getNext();
            header = aux;
            if (size.intValue() == 1) {
                last = null;
            }
            size--;
            return element;
        }
    }

    public E deleteLast() throws ListEmptyException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, lista vacia");
        } else {
            E element = last.getInfo();
            Node<E> aux = getNode(size - 2);
            if(aux == null) {
                last = null;
                if(size == 2) {
                    last = header;
                } else {
                    header = null;
                }
            } else {
                last = null;
                last = aux;
                last.setNext(null);
            }
            size--;
            return element;
        }
    }

    public E delete(Integer post) throws ListEmptyException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, lista vacia");
        } else if (post < 0 || post >= size) {
            throw new IndexOutOfBoundsException("Error, fuera de rango");
        } else if (post == 0) {
            return deleteFirst(); // Llama a deleteFirst para eliminar el primer nodo
        } else if (post == (size - 1)) {
            return deleteLast(); // Llama a deleteLast para eliminar el último nodo
        } else {
            Node<E> preview = getNode(post - 1); // Nodo anterior al que se eliminará
            Node<E> actual = getNode(post); // Nodo que se eliminará
            E element = actual.getInfo(); // Guardar el dato del nodo a eliminar
    
            Node<E> next = actual.getNext(); // Nodo siguiente al que se eliminará
            preview.setNext(next); // Conectar el nodo previo con el siguiente
    
            // Si se elimina un nodo del medio, no necesitamos cambiar last
            size--; // Reducir el tamaño de la lista
            return element; // Retornar el elemento eliminado
        }
    }

    private E getFirst() throws ListEmptyException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, lista vacia");
        }
        return last.getInfo();
    }

    public E getLast() throws ListEmptyException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, Lista Vacia");
        }
        return last.getInfo();
    }

    public E get(Integer index) throws ListEmptyException, IndexOutOfBoundsException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, list empty");
        } else if (index.intValue() < 0 || index.intValue() >= this.size.intValue()) {
            throw new IndexOutOfBoundsException("Error, fuera de rango");
        } else if (index.intValue() == 0) {
            return header.getInfo();
        } else if (index.intValue() == (this.size - 1)) {
            return last.getInfo();
        } else {
            Node<E> search = header;
            int cont = 0;
            while (cont < index.intValue()) {
                cont++;
                search = search.getNext();
            }
            return search.getInfo();
        }
    }

    public void add(E info, Integer index) throws ListEmptyException, IndexOutOfBoundsException {
        if (isEmpty() || index.intValue() == 0) {
            addHeader(info);
        } else if (index.intValue() == this.size.intValue()) {
            addLast(info);
        } else {
            Node<E> search_preview = getNode(index - 1);
            Node<E> search = getNode(index);
            Node<E> help = new Node<>(info, search);
            search_preview.setNext(help);
            this.size++;
        }
    }

    /*** END BYPOSITION */
    public void reset() {
        this.header = null;
        this.last = null;
        this.size = 0;
    }
    

    //nos permite los datos de una lista 
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("List data");
        try {
            Node<E> help = header;
            while (help != null) {
                sb.append(help.getInfo()).append(" -> ");
                help = help.getNext();
            }
        } catch (Exception e) {
            sb.append(e.getMessage());
        }
        return sb.toString();
    }

    public Integer getSize() {
        return this.size;
    }

    public Node<E> getHeader() {
        return header; // Devuelve el nodo cabecera
    }

    // esta bien
    public E[] toArray() {
        E[] matrix = null;
        if (!isEmpty()) {
            Class clazz = header.getInfo().getClass();
            matrix = (E[]) java.lang.reflect.Array.newInstance(clazz, size);
            Node<E> aux = header;
            for (int i = 0; i < this.size; i++) {
                matrix[i] = aux.getInfo();
                aux = aux.getNext();
            }

        }
        return matrix;
    }

    public LinkedList<E> toList(E[] matrix) {
        reset();
        for (int i = 0; i < matrix.length; i++) {
            this.add(matrix[i]);
        }
        return this;
    }
    
    public LinkedList<E> filter(Object data) throws ListEmptyException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, la lista está vacía");
        }
        
        E[] array = toArray();
        LinkedList<E> aux = new LinkedList<>();
        
        for (E element : array) {
            try {
                java.lang.reflect.Field idpropiedadField = element.getClass().getDeclaredField("idpropiedad");
                idpropiedadField.setAccessible(true);
                
                Object idpropiedadValue = idpropiedadField.get(element);
                System.out.println("Comparando idpropiedad: " + idpropiedadValue + " con: " + data); // Debug
                
                if (idpropiedadValue != null && idpropiedadValue.equals(data)) {
                    aux.add(element);
                }
            } catch (NoSuchFieldException e) {
                System.out.println("Campo 'idpropiedad' no encontrado en la clase " + element.getClass().getName());
                continue;
            } catch (IllegalAccessException e) {
                System.out.println("Error de acceso al campo 'idpropiedad' en la clase " + element.getClass().getName());
                continue;
            }
        }
        
        return aux;
    }
    
    
    public boolean exist(Object data) {
        Node<E> node = header;
        while (node != null) {
            try {
                // Verificar si el objeto tiene el campo '_dni'
                java.lang.reflect.Field idProyectoField = node.getInfo().getClass().getDeclaredField("idProyecto");
                idProyectoField.setAccessible(true);
                if (idProyectoField.get(node.getInfo()).equals(data)) {
                    System.out.println("Ya existe un nodo con este dato (_dni)");
                    return true;
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                // Si el campo no existe, ignóralo y continúa
            }
            node = node.getNext();
        }
        return false;
    }
    
    // Cosas Practica_3///////////////////////////////////////////////////////////////////////////////////////

    // Métodos de Búsqueda
    public LinkedList<E> binarySearch(Object criterio, String atributo) throws Exception {
        LinkedList<E> resultados = new LinkedList<>();
        E[] array = toArray();

        // Convertir criterio a minúsculas
        String criterioStr = criterio.toString().toLowerCase();

        // Primero ordenamos por el atributo
        quickSort(atributo, true);
        array = toArray();

        for (E elemento : array) {
            Object valorAtributo = obtenerValorAtributo(elemento, atributo);
            String valorAtributoStr = valorAtributo.toString().toLowerCase();

            if (valorAtributoStr.contains(criterioStr)) {
                resultados.add(elemento);
            }
        }

        return resultados;
    }

    public LinkedList<E> linearSearch(Object criterio, String atributo) throws Exception {
        LinkedList<E> resultados = new LinkedList<>();
        E[] array = toArray();

        for (E elemento : array) {
            Object valorAtributo = obtenerValorAtributo(elemento, atributo);

            // Convertir tanto el valor del atributo como el criterio a cadenas para
            // comparación
            String valorAtributoStr = valorAtributo.toString().toLowerCase();
            String criterioStr = criterio.toString().toLowerCase();

            // Verificar si el criterio está contenido en el valor del atributo
            if (valorAtributoStr.contains(criterioStr)) {
                resultados.add(elemento);
            }
        }

        return resultados;
    }

    // Métodos de Ordenamiento
    public void quickSort(String atributo, boolean ascendente) throws Exception {
        E[] array = toArray();
        quickSortRecursivo(array, 0, array.length - 1, atributo, ascendente);
        toList(array);
    }

    private void quickSortRecursivo(E[] array, int inicio, int fin, String atributo, boolean ascendente)
            throws Exception {
        if (inicio < fin) {
            int indiceParticion = particion(array, inicio, fin, atributo, ascendente);
            quickSortRecursivo(array, inicio, indiceParticion - 1, atributo, ascendente);
            quickSortRecursivo(array, indiceParticion + 1, fin, atributo, ascendente);
        }
    }

    private int particion(E[] array, int inicio, int fin, String atributo, boolean ascendente) throws Exception {
        Object pivote = obtenerValorAtributo(array[fin], atributo);
        int i = inicio - 1;

        for (int j = inicio; j < fin; j++) {
            Object valorActual = obtenerValorAtributo(array[j], atributo);
            int comparacion = compararObjetos(valorActual, pivote);

            if (ascendente ? comparacion <= 0 : comparacion >= 0) {
                i++;
                E temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }

        E temp = array[i + 1];
        array[i + 1] = array[fin];
        array[fin] = temp;

        return i + 1;
    }

    public void mergeSort(String atributo, boolean ascendente) throws Exception {
        E[] array = toArray();
        mergeSortRecursivo(array, 0, array.length - 1, atributo, ascendente);
        toList(array);
    }

    private void mergeSortRecursivo(E[] array, int inicio, int fin, String atributo, boolean ascendente)
            throws Exception {
        if (inicio < fin) {
            int medio = (inicio + fin) / 2;
            mergeSortRecursivo(array, inicio, medio, atributo, ascendente);
            mergeSortRecursivo(array, medio + 1, fin, atributo, ascendente);
            merge(array, inicio, medio, fin, atributo, ascendente);
        }
    }

    private void merge(E[] array, int inicio, int medio, int fin, String atributo, boolean ascendente)
            throws Exception {
        int n1 = medio - inicio + 1;
        int n2 = fin - medio;

        E[] izquierdo = (E[]) new Object[n1];
        E[] derecho = (E[]) new Object[n2];

        System.arraycopy(array, inicio, izquierdo, 0, n1);
        System.arraycopy(array, medio + 1, derecho, 0, n2);

        int i = 0, j = 0, k = inicio;

        while (i < n1 && j < n2) {
            Object valorIzq = obtenerValorAtributo(izquierdo[i], atributo);
            Object valorDer = obtenerValorAtributo(derecho[j], atributo);
            int comparacion = compararObjetos(valorIzq, valorDer);

            if (ascendente ? comparacion <= 0 : comparacion >= 0) {
                array[k++] = izquierdo[i++];
            } else {
                array[k++] = derecho[j++];
            }
        }

        while (i < n1) {
            array[k++] = izquierdo[i++];
        }

        while (j < n2) {
            array[k++] = derecho[j++];
        }
    }

    public void shellSort(String atributo, boolean ascendente) throws Exception {
        E[] array = toArray();
        int n = array.length;

        for (int intervalo = n / 2; intervalo > 0; intervalo /= 2) {
            for (int i = intervalo; i < n; i++) {
                E temp = array[i];
                int j;
                Object valorTemp = obtenerValorAtributo(temp, atributo);

                for (j = i; j >= intervalo; j -= intervalo) {
                    Object valorActual = obtenerValorAtributo(array[j - intervalo], atributo);
                    int comparacion = compararObjetos(valorActual, valorTemp);

                    if (ascendente ? comparacion <= 0 : comparacion >= 0) {
                        break;
                    }
                    array[j] = array[j - intervalo];
                }
                array[j] = temp;
            }
        }

        toList(array);
    }

    // Métodos auxiliares
    private Object obtenerValorAtributo(E objeto, String atributo) throws Exception {
        if (atributo.equals("value")) {
            return objeto; // Retornando valor
        }

        // Metodo de manejo de atributos
        String metodoBusqueda = "get" + atributo.substring(0, 1).toUpperCase() + atributo.substring(1);
        Method metodo = objeto.getClass().getMethod(metodoBusqueda);
        return metodo.invoke(objeto);
    }

    private int compararObjetos(Object obj1, Object obj2) {
        if (obj1 == null || obj2 == null) {
            return obj1 == obj2 ? 0 : (obj1 == null ? -1 : 1);
        }

        if (obj1 instanceof Comparable && obj2 instanceof Comparable) {
            return ((Comparable) obj1).compareTo(obj2);
        }

        return obj1.toString().compareTo(obj2.toString());
    }

}
