import gymnasium as gym
import matplotlib.pyplot as plt

import numpy as np

def render_state(size='normal'):
    figsize=(2.5,2.5) if size=='small' else (4.5,4.5)
    plt.figure(figsize=figsize) 
    plt.axis('off')
    plt.gcf().set_facecolor("#c8e6f7")
    plt.imshow(env.render())
    plt.show()

def visualize_matrix(matrix: np.ndarray, name=''):
    fig, ax = plt.subplots(figsize=(5,5))
    plt.title(name)
    plt.yticks(np.arange(matrix.shape[0]))
    plt.xticks(np.arange(matrix.shape[1]))
    ax.imshow(matrix, cmap=plt.cm.Blues)

    for i in range(matrix.shape[0]):
        for j in range(matrix.shape[1]):
            ax.text(j, i, np.round(matrix[i, j],1), ha="center", va="center", color="grey")

class Q_Agent():
    def __init__(self, env: gym.Env, alpha:int, gamma:int, epsilon:int, n_episodes:int, seed:int=42):
        pass

    def learn(self) -> list:
        '''Returns a list of rewards from training.'''
        rewards = []
        render_state() # prikaz stanja 
        while not done:
            action = env.action_space.sample()
            next_state, reward, done, _, _ = env.step(action)
            state = next_state
            rewards.append(reward)
            
            clear_output(True)  # cemu sluzi ??
            render_state()

        print(rewards)
        return rewards

    def predict(self, state: np.ndarray) -> int:
        '''Returns the policy action from a state. Implements Exploration Exploitation trade-off'''
        for episode in range(self.n_episodes):
            done = False
            while not done:
            # prvo biramo akciju - 60% exploration, 40% exploitation
                if np.random.uniform(0, 1) < self.epsilon:
                    action = env.action_space.sample() # Exploration
                else:
                    action = np.argmax(self.qtable[state, :]) # Exploitation
            
                next_state, reward, done, _, _ = env.step(action)
                # q-learning formula
                return self.qtable[state, action] + self.alpha * (reward + self.gamma * np.max(self.qtable[next_state, :]) - self.qtable[state, action])
                # state = next_state # ova linija koda ide obavezno nakon sto pozovemo q-learning formulu
                # rewards.append(reward)
        
    def n_actions(self) -> int:
        '''Returns number of actions.'''
        return env.action_space.n  # broj mogucih akcija (gore, dole, levo, desno)

    def n_states(self) -> int:
        '''Returns number of states.'''
        return env.observation_space.n # broj pozicija na tabeli
    
    def get_v_table(self):
        pass

    def test_agent(self, n_episode):
        pass


if __name__ == '__main__':
    env = gym.make('FrozenLake-v1', desc=None, map_name="4x4", is_slippery=False, render_mode='rgb_array')
    env.reset(seed=42)
    agent = Q_Agent(env, alpha=0.1, gamma=0.9, epsilon=0.6, n_episodes=1000)
    rewards = agent.learn()
    # TODO: implementiraj trazene zadatke iz notebook fajla