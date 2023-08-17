import { useState } from 'react';

export function useModal() {
  const [isOpen, setIsOpen] = useState(false);

  function openPopUp() {
    setIsOpen(true);
    document.body.style.overflow = 'hidden';
  }

  function closePopUp() {
    setIsOpen(false);
    document.body.style.overflow = 'unset';
  }

  return { isOpen, openPopUp, closePopUp };
}
