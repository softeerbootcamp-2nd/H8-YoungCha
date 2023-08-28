import { useTagSelectContext } from '@/store/useTagSelectContext';
import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

function useTagRefreshNavigator() {
  const navigate = useNavigate();
  const { selectedAge } = useTagSelectContext();
  useEffect(() => {
    if (selectedAge === null) {
      navigate('/model/LX06/guide/age');
    }
  }, [selectedAge]);
}

export default useTagRefreshNavigator;
