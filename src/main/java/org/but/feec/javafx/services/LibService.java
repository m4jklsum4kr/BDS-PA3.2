package org.but.feec.javafx.services;

import org.but.feec.javafx.api.LibBasicView;
import org.but.feec.javafx.api.LibCreateView;
import org.but.feec.javafx.api.LibEditView;
import org.but.feec.javafx.api.LibDetailView;
import org.but.feec.javafx.data.LibRepository;

import java.util.List;

import static org.but.feec.javafx.services.Argon2FactoryService.ARGON2;

/**
 * Class representing business logic on top of the Persons
 */
public class LibService {

    private LibRepository libRepository;

    public LibService(LibRepository libRepository) {
        this.libRepository = libRepository;
    }

    public LibDetailView getPersonDetailView(Long id) {
        return libRepository.findPersonDetailedView(id);
    }

    public List<LibBasicView> getPersonsBasicView() {
        return libRepository.getPersonsBasicView();
    }

    public void createPerson(LibCreateView libCreateView) {
        // the following three lines can be written in one code line (only for more clear explanation it is written in three lines
       /* char[] originalPassword = libCreateView.getPwd();
        char[] hashedPassword = hashPassword(originalPassword);
        libCreateView.setPwd(hashedPassword);*/

        libRepository.createPerson(libCreateView);
    }

    public void editPerson(LibEditView libEditView) {
        libRepository.editPerson(libEditView);
    }

    /**
     * <p>
     * Note: For implementation details see: https://github.com/phxql/argon2-jvm
     * </p>
     *
     * @param password to be hashed
     * @return hashed password
     */
    public char[] hashPassword(char[] password) {
        return ARGON2.hash(10, 65536, 1, password).toCharArray();
    }

}