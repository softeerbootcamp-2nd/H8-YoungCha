package team.youngcha.domain.dictionary.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.youngcha.domain.dictionary.dto.FindDictionaryResponse;
import team.youngcha.domain.dictionary.entity.Dictionary;
import team.youngcha.domain.dictionary.repository.DictionaryRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DictionaryService {

    private final DictionaryRepository dictionaryRepository;

    public List<FindDictionaryResponse> findDictionary() {
        List<FindDictionaryResponse> findDictionaryResponses = new ArrayList<>();

        for (Dictionary dictionary : dictionaryRepository.findAll()) {
            FindDictionaryResponse findDictionaryResponse = new FindDictionaryResponse(dictionary);
            findDictionaryResponses.add(findDictionaryResponse);
        }

        return findDictionaryResponses;
    }
}
