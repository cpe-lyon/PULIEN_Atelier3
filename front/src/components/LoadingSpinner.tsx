import React from 'react';
import { motion } from 'framer-motion';

const loadingContainerVariants = {
  start: {
    transition: {
      staggerChildren: 0.1,
    },
  },
  end: {
    transition: {
      staggerChildren: 0.1,
    },
  },
};

const loadingCircleVariants = {
  start: {
    y: "0%",
  },
  end: {
    y: "100%",
  },
};

const loadingCircleTransition = {
  duration: 0.5,
  repeat: Infinity,
  repeatType: "reverse" as const,  // Ensuring the value matches the expected type
  ease: "easeInOut",
};

const LoadingSpinner: React.FC = () => {
  return (
    <motion.div
      className="flex justify-center items-center h-screen"
      variants={loadingContainerVariants}
      initial="start"
      animate="end"
    >
      {[...Array(3)].map((_, index) => (
        <motion.span
          key={index}
          className="block w-4 h-4 mx-1 bg-white rounded-full"
          variants={loadingCircleVariants}
          transition={loadingCircleTransition}
        />
      ))}
    </motion.div>
  );
};

export default LoadingSpinner;
