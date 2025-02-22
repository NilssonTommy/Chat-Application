/* Communication with PortalConnection */
package com.example;
public class ClientHandler {

    private PortalConnection pc;

    public ClientHandler(PortalConnection pc) {
        this.pc = pc;
    }

    /* Verify username */
    public Boolean checkUsername(User username) {
        if(pc.login(username.getUsername())) {
            return true;
        }
        return false;
    }

    /* Create a new username */
/*     public Boolean createUser(String name) {
        if(pc.createUser(name)) {
            return true;
        }
        return false;
    } */

}
