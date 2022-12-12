package Interfaces;

import Entities.Log;
import java.util.ArrayList;
import java.util.UUID;

public interface ILogServices {
    void deleteLog(UUID logId);
    Log getById(UUID idlog);
    void create(Log log);
    ArrayList<Log> getAll();
    
}
