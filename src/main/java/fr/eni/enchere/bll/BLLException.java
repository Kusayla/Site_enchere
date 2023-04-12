package fr.eni.enchere.bll;

import java.util.ArrayList;
import java.util.List;

public class BLLException extends Exception {
	private static final long serialVersionUID = 1L;
    private List<String> listErrorCodes;
    public BLLException() {
        super();
        this.listErrorCodes=new ArrayList<>();
    }

    public void addError(String code)
    {
        if(!this.listErrorCodes.contains(code))
        {
            this.listErrorCodes.add(code);
        }
    }

    public boolean hasErrors()
    {
        return this.listErrorCodes.size()>0;
    }

    public List<String> getListErrorCodes()
    {
        return this.listErrorCodes;
    }

    public BLLException(String message) {
        super(message);
    }
}