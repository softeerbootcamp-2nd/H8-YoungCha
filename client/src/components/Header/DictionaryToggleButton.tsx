import * as Icon from '../../assets/icons';
import { useState } from 'react';

function DictionaryToggleButton() {
  const [dictionaryOn, setDictionaryOn] = useState(false);

  function toggleDictionary() {
    setDictionaryOn((prevState) => !prevState);
  }
  return (
    <button
      className="flex items-center text-center title5"
      onClick={toggleDictionary}
    >
      {dictionaryOn ? <Icon.DictionaryOnIcon /> : <Icon.DictionaryOffIcon />}
      <span
        className={`pl-4px ${
          dictionaryOn ? 'text-icon-yellow' : 'text-grey-black'
        }`}
      >
        {dictionaryOn ? '백카사전 on' : '백카사전 off'}
      </span>
    </button>
  );
}

export default DictionaryToggleButton;
