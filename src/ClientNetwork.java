public final class ClientNetwork {
    private static ClientNetwork instance;

    private ClientNetwork() {

    }


    Boolean checkUsername(String Username){
       return "tommy".equals(Username);
    };

    public static ClientNetwork getInstance() {
        if (instance == null){
            instance = new ClientNetwork();
        }
        return instance;
    }
}
