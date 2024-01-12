package ua.gaponov.monitor.terminals;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TerminalService {

    private final TerminalRepository terminalRepository;
    private final TerminalMapper terminalMapper;

    public List<TerminalDTO> getAllTerminals(){
        List<Terminal> terminals = getAll();
        return terminals.stream().map(terminalMapper::mapEntityToDto).toList();
    }

    private List<Terminal> getAll(){
        return terminalRepository.findAllByOrderByArmId();
    }

    public void save(TerminalDTO terminalDTO) {
        Terminal terminal = terminalMapper.mapDtoToEntity(terminalDTO);
        terminalRepository.save(terminal);
    }
}
