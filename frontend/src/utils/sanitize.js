/**
 * Sanitize text to prevent XSS attacks
 * Escapes HTML special characters
 */
export const sanitizeText = (text) => {
  if (!text) return '';
  
  const div = document.createElement('div');
  div.textContent = text;
  return div.innerHTML;
};

/**
 * Sanitize and preserve line breaks
 */
export const sanitizeWithLineBreaks = (text) => {
  if (!text) return '';
  
  return sanitizeText(text).replace(/\n/g, '<br/>');
};
