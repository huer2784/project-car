import {describe, test, expect} from 'vitest';
import {render, screen, waitFor} from '@testing-library/react';
import {userEvent} from '@testing-library/user-event';
import '@testing-library/jest-dom';
import Carlist from '../components/Carlist';
import {QueryClient, QueryClientProvider} from '@tanstack/react-query';
import type React from 'react';

const queryClient = new QueryClient({
    defaultOptions  : {
        queries : {
            retry:false,
        }
    }
});


const wrapper = ({children} : {children: React.ReactNode}) => (
    <QueryClientProvider client={queryClient}>
        {children}
    </QueryClientProvider>
    
);

describe("Carlist test",() => {
    test("component renders", () => {
        render(<Carlist/>,{wrapper}); 
        expect(screen.getByText(/Loading/i)).toBeInTheDocument();
    });

    test("Cars are fetched", async () => {
        render(<Carlist />, {wrapper});

        await waitFor(() => screen.getByText(/새 차/i));
        
        const cars = await screen.findAllByText(/ford/i);

        expect(cars.length).toBeGreaterThan(0);
        expect(cars).toHaveLength(3);

        //screen.debug();
        //screen.debug(undefined, Infinity);
  })

  test("Open new car modal", async () => {
        render(<Carlist />, { wrapper });
        await waitFor(() => screen.getByText(/새 차/i));
        await userEvent.click(screen.getByText(/새 차/i));
        expect(screen.getByText(/저장/i)).toBeInTheDocument();
  })  
});