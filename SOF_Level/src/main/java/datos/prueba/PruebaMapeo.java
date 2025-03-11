
package datos.prueba;

import datos.mapeo.Cliente;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class PruebaMapeo {

    public static void main(String[] args) {
        // Crear EntityManagerFactory y EntityManager
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("org.itson_SOF_Level_jar_1.0-VERSIONPU");
        EntityManager em = emf.createEntityManager();

        // Iniciar una transacción
        em.getTransaction().begin();

        // Crear un Cliente
        Cliente cliente = new Cliente();
        cliente.setNombre("Juan Pérez");
        cliente.setTelefono("555-1234");
        cliente.setCorreo("juan.perez@correo.com");

        // Crear algunos contratos
        //Contrato contrato1 = new Contrato();
        //contrato1.setTematica("Contrato de Fotografía Básico");
        //contrato1.setCliente(cliente);

        //Contrato contrato2 = new Contrato();
        //contrato2.setTematica("Contrato de Fotografía Avanzado");
        //contrato2.setCliente(cliente);

        // Añadir los contratos al cliente
        //Set<Contrato> contratos = new HashSet<>();
        //contratos.add(contrato1);
        //contratos.add(contrato2);
        //cliente.setContratos(contratos);

        // Persistir el Cliente (esto también persistirá los Contratos debido a la relación CascadeType.ALL)
        em.persist(cliente);

        // Confirmar la transacción
        em.getTransaction().commit();

        // Verificar si el cliente fue guardado correctamente
        Cliente clientePersistido = em.find(Cliente.class, cliente.getId());
        System.out.println("Cliente guardado: " + clientePersistido.getNombre());

        // Verificar si los contratos se guardaron
        /*
        for (Contrato contrato : clientePersistido.getContratos()) {
            System.out.println("Contrato de cliente: " + contrato.getTematica());
        }*/

        // Cerrar el EntityManager
        em.close();
        emf.close();
    }
}
