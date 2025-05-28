package src.Provider;

import java.sql.Connection;
import src.service.ClientService;
import src.service.CommandePizzaService;
import src.service.IngredientService;
import src.service.LivraisonService;
import src.service.LivreurService;
import src.service.PizzaService;
import src.service.VehiculeService;

public class ServiceProvider {
    private Connection conn;

    public VehiculeService vehiculeService;
    public PizzaService pizzaService;
    public IngredientService ingredientService;
    public LivreurService livreurService;
    public ClientService clientService;
    public LivraisonService livraisonService;
    public CommandePizzaService commandePizzaService;

    public ServiceProvider(Connection pConn) {
        this.conn = pConn;
        this.vehiculeService = new VehiculeService(conn);
        this.clientService = new ClientService(conn);
        this.pizzaService = new PizzaService(conn);
        this.ingredientService = new IngredientService(conn);
        this.livreurService = new LivreurService(conn);
        this.livraisonService = new LivraisonService(conn);
        this.commandePizzaService = new CommandePizzaService(conn);

    }
}